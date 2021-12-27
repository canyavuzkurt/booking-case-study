package com.justlife.bookingcasestudy.repository.aggregatewrapper;

import lombok.AllArgsConstructor;

// Purely for testing
@AllArgsConstructor
public class AvailableVehicleImpl implements AvailableVehicle{

    private Long vehicleId;
    private Integer availableCleaners;

    @Override
    public Long getVehicleId() {
        return vehicleId;
    }

    @Override
    public Integer getAvailableCleaners() {
        return availableCleaners;
    }
}
