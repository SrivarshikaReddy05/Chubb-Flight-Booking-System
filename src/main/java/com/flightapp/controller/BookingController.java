package com.flightapp.controller;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.BookingResponse;
import com.flightapp.dto.PassengerDto;
import com.flightapp.entity.Booking;
import com.flightapp.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/flight")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/booking/{flightId}")
    public ResponseEntity<BookingResponse> bookFlight(
            @PathVariable Long flightId,
            @RequestBody BookingRequest request) {

        Booking booking = bookingService.bookFlight(flightId, request);
        BookingResponse resp = mapToResponse(booking);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<BookingResponse> getTicket(@PathVariable String pnr) {
        Booking booking = bookingService.getBookingByPnr(pnr);
        return ResponseEntity.ok(mapToResponse(booking));
    }

    @GetMapping("/booking/history/{emailId}")
    public ResponseEntity<?> history(@PathVariable String emailId) {
        var list = bookingService.getBookingsByEmail(emailId);
        var respList = list.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(respList);
    }

    @DeleteMapping("/booking/cancel/{pnr}")
    public ResponseEntity<String> cancel(@PathVariable String pnr, @RequestParam String email) {
        bookingService.cancelBooking(pnr, email);
        return ResponseEntity.ok("Cancelled booking " + pnr);
    }

    private BookingResponse mapToResponse(Booking b) {
        BookingResponse br = new BookingResponse();
        br.setPnr(b.getPnr());
        br.setUserName(b.getUserName());
        br.setEmail(b.getEmail());
        br.setSeatsBooked(b.getSeatsBooked());
        br.setTotalAmount(b.getTotalAmount());
        br.setBookingTime(b.getBookingTime());
        br.setJourneyDate(b.getJourneyDate());
        br.setCancelled(b.isCancelled());
        br.setPassengers(b.getPassengers().stream().map(p -> {
            PassengerDto pd = new PassengerDto();
            pd.setName(p.getName());
            pd.setAge(p.getAge());
            pd.setGender(p.getGender());
            pd.setMeal(p.getMeal());
            pd.setSeatNumber(p.getSeatNumber());
            return pd;
        }).collect(Collectors.toList()));
        br.setFlightId(b.getFlight().getId());
        br.setFlightNumber(b.getFlight().getFlightNumber());
        return br;
    }
}
