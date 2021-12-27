package com.justlife.bookingcasestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppointmentNotFound extends RuntimeException{

    public AppointmentNotFound(Long id) {
        super("Appointment with id " + id.toString() + "does not exist.");
    }
}
