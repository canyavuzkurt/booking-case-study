package com.justlife.bookingcasestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalTime;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OutOfWorkingHoursException extends RuntimeException{

    public OutOfWorkingHoursException(LocalTime workingHourStart, LocalTime workingHourEnd) {
        super("Our cleaners only works between " + workingHourStart.toString() + " - " + workingHourEnd.toString());
    }
}
