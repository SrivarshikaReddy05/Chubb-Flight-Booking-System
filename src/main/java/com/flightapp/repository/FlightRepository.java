package com.flightapp.repository;


import com.flightapp.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Searching flight
    List<Flight> findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
            String fromPlace, String toPlace, LocalDateTime start, LocalDateTime end);
}
