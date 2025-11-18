package com.flightapp.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private String fromPlace;
    private String toPlace;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;
    private String airlineName;
    private String airlineLogo;
}
