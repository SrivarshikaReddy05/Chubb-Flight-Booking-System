package com.flightapp.controller;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1.0/flight/airline")
@RequiredArgsConstructor
public class AdminController {

    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;

    @PostMapping("/inventory/add")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        // id null
        if (flight.getAirline() != null && flight.getAirline().getId() == null && flight.getAirline().getName() != null) {
            Airline a = airlineRepository.findByNameIgnoreCase(flight.getAirline().getName())
                    .orElseGet(() -> airlineRepository.save(flight.getAirline()));
            flight.setAirline(a);
        }
        // check seats availability
        if (flight.getAvailableSeats() == null) {
            flight.setAvailableSeats(flight.getTotalSeats());
        }
        Flight saved = flightRepository.save(flight);
        return ResponseEntity.ok(saved);
    }
}
