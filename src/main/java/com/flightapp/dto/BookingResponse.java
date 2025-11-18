package com.flightapp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class BookingResponse {
    private String pnr;
    private String userName;
    private String email;
    private Integer seatsBooked;
    private Double totalAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") // for timestamps
    private LocalDateTime bookingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime journeyDate;

    private boolean cancelled;
    private List<PassengerDto> passengers;
    private Long flightId;
    private String flightNumber;
}
