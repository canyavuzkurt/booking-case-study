package com.justlife.bookingcasestudy.repository;

import com.justlife.bookingcasestudy.model.Cleaner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CleanerRepository extends BaseRepository<Cleaner>{

    List<Cleaner> findAllByOrderByVehicleId();

    @Query(
            value = "SELECT C.* FROM CLEANER C " +
                    "LEFT JOIN CLEANER_APPOINTMENT CA on C.ID = CA.cleaner_id " +
                    "LEFT JOIN APPOINTMENT A on CA.APPOINTMENT_ID = A.ID " +
                    "WHERE (A.ID is null) " +
                    "OR   NOT (A.START <= :end) AND (A.END >= :start)"
            ,
            nativeQuery = true
    )
    List<Cleaner> findAvailableCleaners(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(
            value = "SELECT C.* FROM CLEANER C " +
                    "INNER JOIN VEHICLE V on C.vehicle_id = V.id " +
                    "LEFT JOIN CLEANER_APPOINTMENT CA on C.ID = CA.cleaner_id " +
                    "LEFT JOIN APPOINTMENT A on CA.APPOINTMENT_ID = A.ID " +
                    "WHERE v.id = :vehicleId " +
                    "AND   (A.ID is null) " +
                    "      OR NOT (A.START <= :end) AND (A.END >= :start)"
            ,
            nativeQuery = true
    )
    List<Cleaner> findAvailableCleanersFromVehicle(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("vehicleId") Long vehicleId);

}
