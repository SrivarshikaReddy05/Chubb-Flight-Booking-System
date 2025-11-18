package com.flightapp.service;

import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.entity.Flight;
import java.util.List;

public interface FlightService {
    List<Flight> searchFlights(FlightSearchRequest request);
}
