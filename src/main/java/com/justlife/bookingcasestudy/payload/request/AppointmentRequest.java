package com.justlife.bookingcasestudy.payload.request;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(type = "string", format = "date_time", example = "2021-12-26T08:00:00")
    private LocalDateTime start;
    @NotNull
    private Integer durationInMins;
    @NotNull
    private Integer numOfCleaners;

}
