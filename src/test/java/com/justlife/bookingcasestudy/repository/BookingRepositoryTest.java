package com.justlife.bookingcasestudy.repository;

import com.justlife.bookingcasestudy.model.Appointment;
import com.justlife.bookingcasestudy.model.Cleaner;
import com.justlife.bookingcasestudy.model.Driver;
import com.justlife.bookingcasestudy.model.Vehicle;
import com.justlife.bookingcasestudy.repository.aggregatewrapper.AvailableVehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.initialization-mode=never"
})
class BookingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    @Sql("/data.sql")
    void getAvailableVehicles() {

        // Setup
        List<Appointment> all = appointmentRepository.findAll();

        LocalDateTime start = LocalDateTime.of(2021, 12, 26, 8, 0);
        LocalDateTime end = LocalDateTime.of(2021, 12, 26, 10, 0);
        List<Cleaner> availableCleaners = cleanerRepository.findAvailableCleaners(start, end);
        List<AvailableVehicle> availableVehicles = bookingRepository.getAvailableVehicles(start, end, 1);
        for (AvailableVehicle vehicle :
                availableVehicles) {

            System.out.println(vehicle.getVehicleId());
            System.out.println(vehicle.getAvailableCleaners());
        }
        System.out.println();
    }
}