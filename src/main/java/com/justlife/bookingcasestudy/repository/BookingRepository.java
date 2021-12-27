package com.justlife.bookingcasestudy.repository;

import com.justlife.bookingcasestudy.model.BaseModel;
import com.justlife.bookingcasestudy.model.Vehicle;
import com.justlife.bookingcasestudy.repository.aggregatewrapper.AvailableVehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends BaseRepository<BaseModel> {

    /**
     * Finds the vehicles which has enough cleaners
     * that is availabilty for appointment with
     * given parameters.
     * @param start
     * @param end
     * @param numOfCleaners
     * @return
     */
    @Query(
            value = "SELECT v.ID AS vehicleId, count(DISTINCT c.id) as availableCleaners " +
                    "FROM VEHICLE v " +
                    "INNER JOIN CLEANER C on v.ID = C.VEHICLE_ID " +
                    "LEFT JOIN CLEANER_APPOINTMENT CA on C.ID = CA.cleaner_id " +
                    "LEFT JOIN APPOINTMENT A on CA.APPOINTMENT_ID = A.ID " +
                    "WHERE (A.ID is null) " +
                    "OR   NOT (A.START <= :end) AND (A.END >= :start) " +
                    "GROUP BY v.ID " +
                    "HAVING count(DISTINCT c.ID) >= :numOfCleaners " +
                    "ORDER BY count(DISTINCT c.id) asc",
            nativeQuery = true
    )
    List<AvailableVehicle> getAvailableVehicles(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                                @Param("numOfCleaners") Integer numOfCleaners);

}
