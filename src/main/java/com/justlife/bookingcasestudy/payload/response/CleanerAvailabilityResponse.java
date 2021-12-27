package com.justlife.bookingcasestudy.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justlife.bookingcasestudy.model.Cleaner;
import com.justlife.bookingcasestudy.payload.response.modelresponse.BasicCleanerResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CleanerAvailabilityResponse {

    private BasicCleanerResponse cleaner;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<IntervalResponse> availableIntervals;

    public CleanerAvailabilityResponse(Cleaner cleaner) {

        this.cleaner = new BasicCleanerResponse(cleaner);
        this.availableIntervals = null;
    }

    public CleanerAvailabilityResponse(Cleaner cleaner, List<IntervalResponse> availableIntervals) {

        this.cleaner = new BasicCleanerResponse(cleaner);
        this.availableIntervals = availableIntervals;
    }
}
