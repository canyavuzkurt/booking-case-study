package com.justlife.bookingcasestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Duration;
import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDurationException extends RuntimeException{

    public InvalidDurationException(List<Duration> durations) {
        super("Only appointments with durations of {" + durations.toString() + "} are available.");
    }
}
