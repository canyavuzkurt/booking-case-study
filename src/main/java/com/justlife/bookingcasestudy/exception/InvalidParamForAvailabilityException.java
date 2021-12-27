package com.justlife.bookingcasestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParamForAvailabilityException extends RuntimeException{

    public InvalidParamForAvailabilityException() {
        super("Either give only date or date, time, duration.");
    }
}
