package com.justlife.bookingcasestudy.payload.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class IntervalResponse {

    private LocalTime start;
    private LocalTime end;

    public IntervalResponse(LocalTime start, LocalTime end) {

        this.start = start;
        this.end = end;
    }
}
