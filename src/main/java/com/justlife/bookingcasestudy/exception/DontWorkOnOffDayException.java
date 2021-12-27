package com.justlife.bookingcasestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.DayOfWeek;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DontWorkOnOffDayException extends RuntimeException{

    public DontWorkOnOffDayException(DayOfWeek offDay) {
        super("Our cleaners do not work at " + offDay.toString());
    }
}
