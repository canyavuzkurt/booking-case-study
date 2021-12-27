package com.justlife.bookingcasestudy.service;

import com.justlife.bookingcasestudy.exception.DontWorkOnOffDayException;
import com.justlife.bookingcasestudy.exception.InvalidDurationException;
import com.justlife.bookingcasestudy.exception.InvalidNumOfCleanersException;
import com.justlife.bookingcasestudy.exception.OutOfWorkingHoursException;
import com.justlife.bookingcasestudy.model.Appointment;
import com.justlife.bookingcasestudy.model.Cleaner;
import com.justlife.bookingcasestudy.model.Driver;
import com.justlife.bookingcasestudy.model.Vehicle;
import com.justlife.bookingcasestudy.payload.response.CleanerAvailabilityResponse;
import com.justlife.bookingcasestudy.payload.response.IntervalResponse;
import com.justlife.bookingcasestudy.repository.AppointmentRepository;
import com.justlife.bookingcasestudy.repository.BookingRepository;
import com.justlife.bookingcasestudy.repository.CleanerRepository;
import com.justlife.bookingcasestudy.repository.aggregatewrapper.AvailableVehicleImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CleanerRepository cleanerRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private BookingService bookingService;

    // Test Data
    private List<Cleaner> cleaners;
    private List<Vehicle> vehicles;
    private List<Driver> drivers;
    private List<Appointment> appointments;
    private LocalDate saturday = LocalDate.of(2021, 11, 25);

    @BeforeEach
    void setupMockData() {

        drivers = Stream.of(
                new Driver("Driver1", "Driverson"),
                new Driver("Driver2", "Driverson")
        ).collect(Collectors.toList());

        for (int i = 0; i < drivers.size(); i++) {

            drivers.get(i).setId((long) i+1);
        }

        vehicles = Stream.of(
                new Vehicle(drivers.get(0), null),
                new Vehicle(drivers.get(1), null)
        ).collect(Collectors.toList());

        for (int i = 0; i < vehicles.size(); i++) {

            vehicles.get(i).setId((long) i+1);
        }

        cleaners = Stream.of(
                new Cleaner("Cleaner1-1", "Cleanerson", vehicles.get(0)),
                new Cleaner("Cleaner1-2", "Cleanerson", vehicles.get(0)),
                new Cleaner("Cleaner2-1", "Cleanerson", vehicles.get(1)),
                new Cleaner("Cleaner2-2", "Cleanerson", vehicles.get(1)),
                new Cleaner("Cleaner2-3", "Cleanerson", vehicles.get(1))
        ).collect(Collectors.toList());

        for (int i = 0; i<cleaners.size(); i++) {

            cleaners.get(i).setId((long) i+1);
        }

        LocalTime timeEight = LocalTime.of(8, 0);
        LocalTime timeTen = LocalTime.of(10,  0);
        LocalTime timeTwelve = LocalTime.of(12, 0);
        LocalTime timeFourteen = LocalTime.of(14, 0);
        LocalTime timeEighteen = LocalTime.of(18, 0);
        LocalTime timeTwentyTwo = LocalTime.of(22, 0);
        appointments = Stream.of(
                new Appointment(cleaners.subList(0, 2), LocalDateTime.of(saturday, timeEight), LocalDateTime.of(saturday, timeTen)),
                new Appointment(cleaners.subList(2, 4), LocalDateTime.of(saturday, timeTen), LocalDateTime.of(saturday, timeTwelve)),
                new Appointment(cleaners.subList(2, 4), LocalDateTime.of(saturday, timeFourteen), LocalDateTime.of(saturday, timeEighteen)),
                new Appointment(cleaners.subList(4, 5), LocalDateTime.of(saturday, timeFourteen), LocalDateTime.of(saturday, timeEighteen))

        ).collect(Collectors.toList());

        for (int i = 0; i<appointments.size(); i++) {

            appointments.get(i).setId((long) i+1);
        }
    }

    @Test
    void findAllAppointmentsHappy() {

        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> testResult = bookingService.findAllAppointments();

        for (int i = 0; i < appointments.size(); i++) {

            assertEquals(appointments.get(i), testResult.get(i));
        }
    }

    @Test
    void getAvailableCleanersWithTimeHappy() {

        // Setup
        when(cleanerRepository.findAllByOrderByVehicleId()).thenReturn(cleaners);
        LocalDate saturday = LocalDate.of(2021, 11, 25);
        when(appointmentRepository.findBetween(
                LocalDateTime.of(saturday, bookingService.WORKING_HOUR_START),
                LocalDateTime.of(saturday, bookingService.WORKING_HOUR_END))
        ).thenReturn(appointments.subList(0, 4));

        // Call
        List<CleanerAvailabilityResponse> testResult = bookingService.getAvailableCleanersWithTime(saturday);

        //Assert
        assertEquals(new IntervalResponse(LocalTime.of(10, 30), LocalTime.of(22, 0)), testResult.get(0).getAvailableIntervals().get(0));
        assertEquals(new IntervalResponse(LocalTime.of(8, 0), LocalTime.of(9, 30)), testResult.get(2).getAvailableIntervals().get(0));
        assertEquals(new IntervalResponse(LocalTime.of(12, 30), LocalTime.of(13, 30)), testResult.get(2).getAvailableIntervals().get(1));
        assertEquals(new IntervalResponse(LocalTime.of(18, 30), LocalTime.of(22, 0)), testResult.get(2).getAvailableIntervals().get(2));
        assertEquals(new IntervalResponse(LocalTime.of(8, 0), LocalTime.of(13, 30)), testResult.get(4).getAvailableIntervals().get(0));
        assertEquals(new IntervalResponse(LocalTime.of(18, 30), LocalTime.of(22, 0)), testResult.get(4).getAvailableIntervals().get(1));

    }

    @Test
    void getAvailableCleanersHappy() {

        // Setup
        LocalTime timeEight = LocalTime.of(8, 0);
        Duration duration = Duration.ofHours(4);
        LocalDateTime start = LocalDateTime.of(saturday, timeEight).minus(bookingService.BREAK_DURATION);
        LocalDateTime end = LocalDateTime.of(saturday, timeEight.plus(duration)).plus(bookingService.BREAK_DURATION);
        when(cleanerRepository.findAvailableCleaners(start, end)).thenReturn(Stream.of(cleaners.get(4)).collect(Collectors.toList()));

        // Call
        List<CleanerAvailabilityResponse> testResult = bookingService.getAvailableCleaners(saturday, timeEight, duration);

        // Assert
        assertEquals(1, testResult.size());
        assertEquals(cleaners.get(4).getFName(), testResult.get(0).getCleaner().getFName());
    }

    @Test
    void createNewAppointmentHappy() {

        // Setup
        LocalTime timeTenThirty = LocalTime.of(10, 30);
        Duration duration = Duration.ofHours(4);
        Integer numOfCleaners = 2;
        Long vehicleId = 1L;
        LocalDateTime start = LocalDateTime.of(saturday, timeTenThirty);
        LocalDateTime appAvailabilityStart = start.minus(bookingService.BREAK_DURATION);
        LocalDateTime appAvailabilityEnd = start.plus(duration).plus(bookingService.BREAK_DURATION);
        Appointment expected = new Appointment(cleaners.subList(0, 2), start, start.plus(duration));
        when(bookingRepository.getAvailableVehicles(appAvailabilityStart, appAvailabilityEnd, numOfCleaners))
                .thenReturn(Stream.of(
                        new AvailableVehicleImpl(vehicleId, 2)
                ).collect(Collectors.toList()));
        when(cleanerRepository.findAvailableCleanersFromVehicle(appAvailabilityStart, appAvailabilityEnd, vehicleId))
                .thenReturn(cleaners.subList(0, 2));
        when(appointmentRepository.save(Mockito.any(Appointment.class)))
                .thenReturn(expected);

        // Call
        Appointment result = bookingService.createNewAppointment(start, duration, numOfCleaners);

        // Assert
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    void editAppointmentHappy() {

        // Setup
        Appointment app = appointments.get(0);
        LocalDateTime start = app.getStart().plus(Duration.ofHours(2));
        Integer numOfCleaners = 2;
        Long vehicleId = 1L;
        Duration duration = Duration.ofHours(2);
        Appointment expected = new Appointment(app.getCleaners(), start, start.plus(duration));
        expected.setId(app.getId());
        when(bookingRepository.getAvailableVehicles(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(Stream.of(
                        new AvailableVehicleImpl(vehicleId, numOfCleaners)
                ).collect(Collectors.toList()));
        when(cleanerRepository.findAvailableCleanersFromVehicle(Mockito.any(), Mockito.any(), Mockito.anyLong()))
                .thenReturn(cleaners.subList(0, 2));
        when(appointmentRepository.findById(app.getId())).thenReturn(Optional.of(app));
        when(appointmentRepository.save(Mockito.any(Appointment.class)))
                .thenReturn(expected);

        // Call
        Appointment testResult = bookingService.editAppointment(app.getId(), start, duration, numOfCleaners);

        // Assert
        assertEquals(expected.getId(), testResult.getId());
        assertEquals(expected.getStart(), testResult.getStart());
        assertEquals(expected.getEnd(), testResult.getEnd());

    }

    @ParameterizedTest
    @MethodSource("provideAppointmentParamsForException")
    void validateAppointmentParamsThrowsException(LocalDateTime dateTime, Duration duration, Integer numOfCleaners, Class exceptionClass) {

        assertThrows(
                exceptionClass,
                () -> bookingService.createNewAppointment(dateTime, duration, numOfCleaners));

    }

    private static Stream<Arguments> provideAppointmentParamsForException() {

        return Stream.of(
                Arguments.of(LocalDateTime.of(2021, 12, 24, 8, 0), null, null, DontWorkOnOffDayException.class),
                Arguments.of(LocalDateTime.of(2021, 12, 25, 7, 0), Duration.ofMinutes(5), null, OutOfWorkingHoursException.class),
                Arguments.of(LocalDateTime.of(2021, 12, 25, 8, 0), Duration.ofMinutes(5), null, InvalidDurationException.class),
                Arguments.of(LocalDateTime.of(2021, 12, 25, 8, 0), Duration.ofHours(2), -1, InvalidNumOfCleanersException.class)
        );
    }
}