package com.justlife.bookingcasestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughCleanersException extends RuntimeException{

    public NotEnoughCleanersException() {
        super("There is not enough cleaners for the given date and time.");
    }
}
