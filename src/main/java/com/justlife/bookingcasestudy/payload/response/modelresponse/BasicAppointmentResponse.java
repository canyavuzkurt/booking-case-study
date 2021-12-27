package com.justlife.bookingcasestudy.payload.response.modelresponse;

import com.justlife.bookingcasestudy.model.Appointment;
import com.justlife.bookingcasestudy.model.Cleaner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class BasicAppointmentResponse {

    private Long id;
    private List<BasicCleanerResponse> cleaners;
    private LocalDateTime start;
    private LocalDateTime end;

    public BasicAppointmentResponse(Appointment appointment) {
        List<BasicCleanerResponse> cleaners = appointment.getCleaners().stream().map(BasicCleanerResponse::new).collect(Collectors.toList());
        setId(appointment.getId());
        setCleaners(cleaners);
        setStart(appointment.getStart());
        setEnd(appointment.getEnd());
    }
}
