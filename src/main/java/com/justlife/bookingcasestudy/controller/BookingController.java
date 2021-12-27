package com.justlife.bookingcasestudy.controller;

import com.justlife.bookingcasestudy.exception.InvalidParamForAvailabilityException;
import com.justlife.bookingcasestudy.model.Appointment;
import com.justlife.bookingcasestudy.payload.request.AppointmentRequest;
import com.justlife.bookingcasestudy.payload.response.CleanerAvailabilityResponse;
import com.justlife.bookingcasestudy.payload.response.modelresponse.BasicAppointmentResponse;
import com.justlife.bookingcasestudy.service.BookingService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest Controller for Booking Operations
 */
@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Custom - Gets all the appointments in the system

    /**
     * @return List of all the appointments in the system
     */
    @GetMapping("/appointments")
    public List<BasicAppointmentResponse> getAppointments() {

        return bookingService.findAllAppointments().stream().map(BasicAppointmentResponse::new).collect(Collectors.toList());
    }

    /**
     * Allows the users to check availability of appointments.
     * Either only date is given or all of the parameters are.
     * @param date date of the appointment
     * @param time optional parameter for appointment startTime
     * @param duration optional parameter for appointment duration
     * @return List of cleaners with their available time intervals.
     */
    @GetMapping("/availability")
    public List<CleanerAvailabilityResponse> getAvailability(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Schema(type = "string", format = "date", example = "2021-12-26") LocalDate date,
            @RequestParam(name = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @Schema(type = "string", format = "time", example = "08:00:00") LocalTime time,
            @RequestParam(name = "durationInMins", required = false) Integer duration) {

        if (time == null && duration == null) {

            return bookingService.getAvailableCleanersWithTime(date);
        } else if (time != null && duration != null) {

            return bookingService.getAvailableCleaners(date, time, Duration.ofMinutes(duration));
        } else {

            throw new InvalidParamForAvailabilityException();
        }

    }

    /**
     * Creates a new appointment with regards to the desired startDateTime, duration and numberOfCleaners
     * @param request desired body of the post request
     * @return Information about the created Appointment
     */
    @PostMapping("")
    public BasicAppointmentResponse newAppointment(
            @RequestBody AppointmentRequest request) {

        Appointment newAppointment = bookingService.createNewAppointment(request.getStart(), Duration.ofMinutes(request.getDurationInMins()), request.getNumOfCleaners());
        return new BasicAppointmentResponse(newAppointment);
    }

    /**
     * Modifies an appointment
     * @param id id of the appointment
     * @param request desired body of the post request
     * @return Information about the modified Appointment
     */
    @PutMapping("/{appointment-id}")
    public BasicAppointmentResponse updateAppointment(
            @PathVariable("appointment-id") Long id,
            @RequestBody AppointmentRequest request) {

        Appointment editedAppointment = bookingService.editAppointment(id, request.getStart(), Duration.ofMinutes(request.getDurationInMins()), request.getNumOfCleaners());
        return new BasicAppointmentResponse(editedAppointment);
    }
}

