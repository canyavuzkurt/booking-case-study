package com.justlife.bookingcasestudy.repository;

import com.justlife.bookingcasestudy.model.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends BaseRepository<Appointment> {

    @Query(
            "SELECT a FROM Appointment a WHERE a.start <= :end AND a.end >= :start ORDER BY a.start asc"
    )
    List<Appointment> findBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
