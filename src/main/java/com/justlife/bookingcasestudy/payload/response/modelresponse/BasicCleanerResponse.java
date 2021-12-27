package com.justlife.bookingcasestudy.payload.response.modelresponse;

import com.justlife.bookingcasestudy.model.Cleaner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicCleanerResponse extends BasicPersonResponse{

    public Long vehicleId;

    public BasicCleanerResponse(Cleaner cleaner) {

        super(cleaner);
        if (cleaner.getVehicle() != null)
            setVehicleId(cleaner.getVehicle().getId());
    }
}
