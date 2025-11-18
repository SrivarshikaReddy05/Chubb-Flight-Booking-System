package com.flightapp.service.impl;

import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.entity.Flight;
import com.flightapp.repository.FlightRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FlightServiceImplTest {

    private FlightRepository flightRepo;
    private FlightServiceImpl service;

    @BeforeEach
    void setup() {
        flightRepo = mock(FlightRepository.class);
        service = new FlightServiceImpl(flightRepo);
    }

    @Test
    void testSearchFlights() {

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AI101");

        when(flightRepo.findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
                anyString(), anyString(), any(), any()))
                .thenReturn(List.of(flight));

        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("Hyd");
        req.setToPlace("Del");
        req.setJourneyDate(LocalDate.now());

        List<Flight> result = service.searchFlights(req);

        assertEquals(1, result.size());
        assertEquals("AI101", result.get(0).getFlightNumber());
    }
}
