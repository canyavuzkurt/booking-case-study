package com.justlife.bookingcasestudy.service;

import com.justlife.bookingcasestudy.exception.*;
import com.justlife.bookingcasestudy.model.Appointment;
import com.justlife.bookingcasestudy.model.Cleaner;
import com.justlife.bookingcasestudy.payload.response.CleanerAvailabilityResponse;
import com.justlife.bookingcasestudy.payload.response.IntervalResponse;
import com.justlife.bookingcasestudy.repository.AppointmentRepository;
import com.justlife.bookingcasestudy.repository.aggregatewrapper.AvailableVehicle;
import com.justlife.bookingcasestudy.repository.BookingRepository;
import com.justlife.bookingcasestudy.repository.CleanerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class that handles the booking logic
 */
@Service
@RequiredArgsConstructor
public class BookingService {

    // Constants
    public static Duration BREAK_DURATION = Duration.ofMinutes(30);
    public static DayOfWeek OFF_DAY = DayOfWeek.FRIDAY;
    public static LocalTime WORKING_HOUR_START = LocalTime.of(8, 0);
    public static LocalTime WORKING_HOUR_END = LocalTime.of(22, 0);
    public static Integer MAX_PROFESSIONAL_PER_APPOINTMENT = 3;
    public static List<Duration> POSSIBLE_APP_DURATIONS = Stream.of(
                                                                    Duration.ofHours(2),
                                                                    Duration.ofHours(4)
                                                                    ).collect(Collectors.toList());

    // Beans
    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;
    private final AppointmentRepository appointmentRepository;

    /**
     * Retrieves all appointments.
     * @return all appointments
     */
    @Transactional(readOnly = true)
    public List<Appointment> findAllAppointments() {

        return appointmentRepository.findAll();
    }

    /**
     * Retrieves availability of the cleaners for the given date.
     * This function also return the cleaners available time intervals.
     * @param date date for which the availability is checked
     * @return Available Cleaners and their Available intervals
     */
    @Transactional(readOnly = true)
    public List<CleanerAvailabilityResponse> getAvailableCleanersWithTime(LocalDate date) {

        validateAppointmentParams(date);

        List<Cleaner> cleaners = cleanerRepository.findAllByOrderByVehicleId();
        Map<Long, Set<Appointment>> cleanerAppointmentMapForDate = getCleanerAppointmentMapForDate(date);

        List<CleanerAvailabilityResponse> result = new ArrayList<>();
        for (Cleaner cleaner : cleaners) {

            List<IntervalResponse> availableIntervals = getIntervalResponsesForCleaner(cleanerAppointmentMapForDate,
                    cleaner);

            result.add(new CleanerAvailabilityResponse(cleaner, availableIntervals));
        }

        return result;
    }

    /**
     * Retrieves availability of the cleaners for the given date, time and duration.
     * This only retrieves the available cleaners and no their intervals
     * because the interval is implicit for all.
     * @param date date for which the availability is checked
     * @param time time for which the availability is checked
     * @param duration duration of service for which the availability is checked
     * @return Available Cleaners
     */
    @Transactional(readOnly = true)
    public List<CleanerAvailabilityResponse> getAvailableCleaners(LocalDate date, LocalTime time, Duration duration) {

        validateAppointmentParams(date, time, duration);

        LocalDateTime start = LocalDateTime.of(date, time).minus(BREAK_DURATION);
        LocalDateTime end = LocalDateTime.of(date, time.plus(duration)).plus(BREAK_DURATION);
        List<Cleaner> availableCleaners = cleanerRepository.findAvailableCleaners(start, end);

        return availableCleaners.stream()
                .map(c -> new CleanerAvailabilityResponse(c))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether an appointment can be made with given parameters and
     * either creates or throws a runtime exception that is returned to the user
     * with the necessary status and message.
     * @param start
     * @param duration
     * @param numOfCleaners
     * @return Created Appointment
     */
    @Transactional(readOnly = false)
    public Appointment createNewAppointment(LocalDateTime start, Duration duration, Integer numOfCleaners) {

        validateAppointmentParams(start.toLocalDate(), start.toLocalTime(), duration, numOfCleaners);

        Appointment app = new Appointment();
        return fillAndSaveAppointment(start, duration, numOfCleaners, app);
    }

    /**
     * Checks whether an appointment can be modified with given parameters and
     * either modifies or throws a runtime exception that is returned to the user
     * with the necessary status and message.
     * @param id
     * @param start
     * @param duration
     * @param numOfCleaners
     * @return Modified Appointment
     */
    @Transactional(readOnly = false)
    public Appointment editAppointment(Long id, LocalDateTime start, Duration duration, Integer numOfCleaners) {

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        Appointment appointment =  optionalAppointment.orElseThrow(() -> new AppointmentNotFound(id));
        LocalDateTime oldStart = appointment.getStart();
        Duration oldDuration = Duration.between(oldStart, appointment.getEnd());
        validateAppointmentParams(oldStart.toLocalDate(), oldStart.toLocalTime(), oldDuration);

        // Remove the current cleaners so their schedule clears.
        appointment.setCleaners(new ArrayList<>());
        appointmentRepository.save(appointment);

        return fillAndSaveAppointment(start, duration, numOfCleaners, appointment);
    }

    /**
     * Helper function used for both create and edit Appointment.
     * It finds the available vehicles and assign the number of cleaners requested
     * or throws an exception.
     * Available vehicle is a vehicle in which has enough available cleaners for given
     * parameters.
     * @param start
     * @param duration
     * @param numOfCleaners
     * @param app
     * @return Saved Appointment
     */
    private Appointment fillAndSaveAppointment(LocalDateTime start, Duration duration, Integer numOfCleaners, Appointment app) {
        LocalDateTime appAvailabilityStart = start.minus(BREAK_DURATION);
        LocalDateTime appAvailabilityEnd = start.plus(duration).plus(BREAK_DURATION);
        List<AvailableVehicle> availableVehicles = bookingRepository.getAvailableVehicles(appAvailabilityStart, appAvailabilityEnd, numOfCleaners);

        if (availableVehicles.size()>0) {

            AvailableVehicle vehicleToAppoint = availableVehicles.get(0);
            List<Cleaner> availableCleaners = cleanerRepository.findAvailableCleanersFromVehicle(appAvailabilityStart, appAvailabilityEnd, vehicleToAppoint.getVehicleId());
            List<Cleaner> cleanersToAppoint = availableCleaners.subList(0, numOfCleaners);

            app.setCleaners(cleanersToAppoint);
            app.setStart(start);
            app.setEnd(start.plus(duration));
            app = appointmentRepository.save(app);
        } else {

            throw new NotEnoughCleanersException();
        }

        return app;
    }

    /**
     * Creates the mapping of cleaners to their appointments for the given date
     * @param date
     * @return Mapping of Cleaners to their Filtered Appointments
     */
    private Map<Long, Set<Appointment>> getCleanerAppointmentMapForDate(LocalDate date) {

        LocalDateTime start = LocalDateTime.of(date, WORKING_HOUR_START);
        LocalDateTime end = LocalDateTime.of(date, WORKING_HOUR_END);
        List<Appointment> appointmentsOfDate = appointmentRepository.findBetween(start, end);

        Map<Long, Set<Appointment>> cleanerAppointmentMap = new HashMap<>();

        for (Appointment appointment : appointmentsOfDate) {

            List<Cleaner> cleanersOfAppointment = appointment.getCleaners();
            for (Cleaner cleaner : cleanersOfAppointment) {

                Set<Appointment> cleanerAppointments = cleanerAppointmentMap.computeIfAbsent(
                        cleaner.getId(), key -> new HashSet<>());
                cleanerAppointments.add(appointment);
            }
        }

        return cleanerAppointmentMap;
    }

    /**
     * Helper function that calculates the available intervals
     * of the cleaner. Ex: 08:00-12:00, 14:30-22:00
     * @param cleanerAppointmentMapForDate maps the cleaners to their filtered appointments
     * @param cleaner
     * @return
     */
    private List<IntervalResponse> getIntervalResponsesForCleaner(
            Map<Long, Set<Appointment>> cleanerAppointmentMapForDate, Cleaner cleaner) {

        List<IntervalResponse> availableIntervals = new ArrayList<>();

        boolean cleanerHasAppointment = cleanerAppointmentMapForDate.containsKey(cleaner.getId());
        if (cleanerHasAppointment) {

            calcIntervalsForCleanerWithAppointments(cleanerAppointmentMapForDate, cleaner, availableIntervals);

        } else {

            availableIntervals.add(new IntervalResponse(WORKING_HOUR_START, WORKING_HOUR_END));
        }
        return availableIntervals;
    }

    /**
     * Helper function that calculates the available intervals for a cleaner
     * that has other appointments that day
     * @param cleanerAppointmentMapForDate
     * @param cleaner
     * @param availableIntervals
     */
    private void calcIntervalsForCleanerWithAppointments(Map<Long, Set<Appointment>> cleanerAppointmentMapForDate, Cleaner cleaner, List<IntervalResponse> availableIntervals) {
        Set<Appointment> appointmentSet = cleanerAppointmentMapForDate.get(cleaner.getId());
        List<Appointment> sortedAppointments = appointmentSet.stream().sorted(Comparator.comparing(Appointment::getStart)).collect(Collectors.toList());

        LocalTime intervalStart = WORKING_HOUR_START;
        for (Appointment appointment : sortedAppointments) {

            LocalTime intervalEnd = appointment.getStart().toLocalTime().minus(BREAK_DURATION);

            if (intervalEnd.compareTo(intervalStart) > 0) {
                availableIntervals.add(new IntervalResponse(intervalStart, intervalEnd));
            }

            intervalStart = appointment.getEnd().toLocalTime().plus(BREAK_DURATION);
        }

        if (intervalStart.compareTo(WORKING_HOUR_END) < 0) {

            availableIntervals.add(new IntervalResponse(intervalStart, WORKING_HOUR_END));
        }
    }

    /**
     * Convenience function that delegates to the most extended polymorph of its kind
     * @param date
     */
    private void validateAppointmentParams(LocalDate date) {

        validateAppointmentParams(date, null, null);
    }

    /**
     * Convenience function that delegates to the most extended polymorph of its kind
     * @param date
     * @param time
     * @param duration
     */
    private void validateAppointmentParams(LocalDate date, LocalTime time, Duration duration) {

        validateAppointmentParams(date, time, duration, null);
    }

    /**
     * Validates the given parameters with respect to constants.
     * Throws exception if any of the parameters are invalid.
     * @param date
     * @param time
     * @param duration
     * @param numOfCleaners
     */
    private void validateAppointmentParams(LocalDate date, LocalTime time, Duration duration, Integer numOfCleaners) {

        if (date != null && date.getDayOfWeek().equals(OFF_DAY)) {

            throw new DontWorkOnOffDayException(OFF_DAY);
        }

        if (time != null && duration != null && (time.compareTo(WORKING_HOUR_START) < 0 || time.plus(duration).compareTo(WORKING_HOUR_END) > 0)) {

            throw new OutOfWorkingHoursException(WORKING_HOUR_START, WORKING_HOUR_END);
        }

        if (duration != null && !POSSIBLE_APP_DURATIONS.contains(duration)) {

            throw new InvalidDurationException(POSSIBLE_APP_DURATIONS);
        }

        if (numOfCleaners != null && (numOfCleaners < 0 || numOfCleaners > MAX_PROFESSIONAL_PER_APPOINTMENT)) {

            throw new InvalidNumOfCleanersException(MAX_PROFESSIONAL_PER_APPOINTMENT);
        }
    }
}
