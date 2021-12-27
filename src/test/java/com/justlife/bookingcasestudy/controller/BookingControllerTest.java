package com.justlife.bookingcasestudy.controller;

import com.justlife.bookingcasestudy.model.Appointment;
import com.justlife.bookingcasestudy.model.Cleaner;
import com.justlife.bookingcasestudy.payload.request.AppointmentRequest;
import com.justlife.bookingcasestudy.payload.response.CleanerAvailabilityResponse;
import com.justlife.bookingcasestudy.payload.response.IntervalResponse;
import com.justlife.bookingcasestudy.service.BookingService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @MockBean
    private BookingService bookingService;
    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void getAppointments() {

        List<Cleaner> cleaners = Stream.of(new Cleaner("test", "testson", null)).collect(Collectors.toList());
        LocalDateTime now = LocalDateTime.of(2021, 12, 26, 8, 0);
        Appointment appointment = new Appointment(cleaners, now, now.plusHours(2L));
        List<Appointment> appointments = Stream.of(appointment).collect(Collectors.toList());
        when(bookingService.findAllAppointments()).thenReturn(appointments);

        mockMvc.perform(get("/booking/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].start").value("2021-12-26T08:00:00"))
                .andExpect(jsonPath("$[0].end").value("2021-12-26T10:00:00"));
    }

    @SneakyThrows
    @Test
    void getAvailabilityWithDate() {

        Cleaner cleaner = new Cleaner("test", "testson", null);
        LocalTime intervalStart = LocalTime.of(8, 0);
        LocalTime intervalEnd = LocalTime.of(10, 0);
        List<IntervalResponse> intervalResponses = Stream.of(
                new IntervalResponse(intervalStart, intervalEnd),
                new IntervalResponse(intervalStart, intervalEnd)
                ).collect(Collectors.toList());
        LocalDate date = LocalDate.of(2021, 12, 26);
        List<CleanerAvailabilityResponse> response = Stream.of(new CleanerAvailabilityResponse(cleaner, intervalResponses)).collect(Collectors.toList());
        when(bookingService.getAvailableCleanersWithTime(date)).thenReturn(response);

        mockMvc.perform(get("/booking/availability")
                .param("date", "2021-12-26"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cleaner.fname").value("test"))
                .andExpect(jsonPath("$[0].cleaner.lname").value("testson"))
                .andExpect(jsonPath("$[0].availableIntervals[0].start").value("08:00:00"))
                .andExpect(jsonPath("$[0].availableIntervals[0].end").value("10:00:00"))
                .andExpect(jsonPath("$[0].availableIntervals[1].start").value("08:00:00"))
                .andExpect(jsonPath("$[0].availableIntervals[1].end").value("10:00:00"));
    }

    @SneakyThrows
    @Test
    void getAvailabilityWithDateTimeDuration() {

        Cleaner cleaner1 = new Cleaner("test1", "testson", null);
        Cleaner cleaner2 = new Cleaner("test2", "testson", null);
        List<CleanerAvailabilityResponse> response = Stream.of(
                new CleanerAvailabilityResponse(cleaner1),
                new CleanerAvailabilityResponse(cleaner2)
        ).collect(Collectors.toList());
        LocalDate date = LocalDate.of(2021, 12, 26);
        LocalTime time = LocalTime.of(8, 0);
        Duration duration = Duration.ofHours(2);
        when(bookingService.getAvailableCleaners(date, time, duration)).thenReturn(response);

        mockMvc.perform(get("/booking/availability")
                .param("date", "2021-12-26")
                .param("startTime", "08:00:00")
                .param("durationInMins", "120"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cleaner.fname").value("test1"))
                .andExpect(jsonPath("$[0].cleaner.lname").value("testson"))
                .andExpect(jsonPath("$[1].cleaner.fname").value("test2"))
                .andExpect(jsonPath("$[1].cleaner.lname").value("testson"));
    }

    @SneakyThrows
    @Test
    void getAvailabilityInvalidPramReturnsBadRequest() {

        mockMvc.perform(get("/booking/availability")
                .param("date", "2021-12-26")
                .param("startTime", "08:00:00"))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void newAppointment() {

        Cleaner cleaner = new Cleaner("test", "testson", null);
        LocalDateTime date = LocalDateTime.of(2021, 12, 26, 8, 0);
        AppointmentRequest request = new AppointmentRequest(date, 120, 2);
        Appointment appointment = new Appointment(Stream.of(cleaner).collect(Collectors.toList()), date, date.plusHours(2));
        when(bookingService.createNewAppointment(request.getStart(), Duration.ofMinutes(request.getDurationInMins()), request.getNumOfCleaners()))
                .thenReturn(appointment);

        mockMvc.perform(post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"start\": \"2021-12-26T08:00:00\", \"durationInMins\": \"120\", \"numOfCleaners\": \"2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cleaners[0].fname").value("test"))
                .andExpect(jsonPath("$.start").value("2021-12-26T08:00:00"))
                .andExpect(jsonPath("$.end").value("2021-12-26T10:00:00"));
    }

    @SneakyThrows
    @Test
    void updateAppointment() {

        Cleaner cleaner = new Cleaner("test", "testson", null);
        LocalDateTime date = LocalDateTime.of(2021, 12, 26, 8, 0);
        AppointmentRequest request = new AppointmentRequest(date, 120, 2);
        Appointment appointment = new Appointment(Stream.of(cleaner).collect(Collectors.toList()), date, date.plusHours(2));
        appointment.setId(1L);
        when(bookingService.editAppointment(appointment.getId(), request.getStart(), Duration.ofMinutes(request.getDurationInMins()), request.getNumOfCleaners()))
                .thenReturn(appointment);

        mockMvc.perform(put("/booking/{appointment-id}", appointment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"start\": \"2021-12-26T08:00:00\", \"durationInMins\": \"120\", \"numOfCleaners\": \"2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cleaners[0].fname").value("test"))
                .andExpect(jsonPath("$.start").value("2021-12-26T08:00:00"))
                .andExpect(jsonPath("$.end").value("2021-12-26T10:00:00"));
    }
}