package com.flightapp.service.impl;

import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.entity.Flight;
import com.flightapp.repository.FlightRepository;
import com.flightapp.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<Flight> searchFlights(FlightSearchRequest req) {
        LocalDateTime startOfDay = req.getJourneyDate().atStartOfDay();
        LocalDateTime endOfDay = req.getJourneyDate().atTime(23, 59, 59);

        List<Flight> outboundFlights = flightRepository
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
                        req.getFromPlace(),
                        req.getToPlace(),
                        startOfDay,
                        endOfDay
                );

        if (req.isRoundTrip() && req.getReturnDate() != null) {
            LocalDateTime returnStart = req.getReturnDate().atStartOfDay();
            LocalDateTime returnEnd = req.getReturnDate().atTime(23, 59, 59);

            List<Flight> returnFlights = flightRepository
                    .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
                            req.getToPlace(),
                            req.getFromPlace(),
                            returnStart,
                            returnEnd
                    );

            outboundFlights.addAll(returnFlights);
        }

        return outboundFlights;
    }
}
