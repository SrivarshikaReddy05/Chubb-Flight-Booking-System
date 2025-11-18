package com.flightapp.dto;

import lombok.Data;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class FlightSearchRequest {
    private String fromPlace;
    private String toPlace;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate journeyDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;  // for round-trip, optional

    private boolean roundTrip;     // true if round-trip search
}
