package com.flightapp.service;

import java.util.List;

import com.flightapp.dto.BookingRequest; // DTO you will create
import com.flightapp.entity.Booking;

public interface BookingService {
    Booking bookFlight(Long flightId, BookingRequest request);
    Booking getBookingByPnr(String pnr);
    List<Booking> getBookingsByEmail(String email);
    void cancelBooking(String pnr, String requestedByEmail);
}
