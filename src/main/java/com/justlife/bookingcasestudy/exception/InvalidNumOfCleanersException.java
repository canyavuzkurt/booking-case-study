package com.justlife.bookingcasestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidNumOfCleanersException extends RuntimeException{

    public InvalidNumOfCleanersException(Integer maxProfessionalPerAppointment) {
        super("An appointment can only have between 0-" + maxProfessionalPerAppointment.toString() + " number of professionals.");
    }
}
