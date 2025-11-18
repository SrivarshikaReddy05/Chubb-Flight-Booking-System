package com.flightapp.service.impl;

import com.flightapp.dto.BookingRequest;
import com.flightapp.entity.Booking;
import com.flightapp.entity.Flight;
import com.flightapp.entity.Passenger;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.service.BookingService;
import com.flightapp.utils.PNRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public Booking bookFlight(Long flightId, BookingRequest request) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));

        if (request.getSeats() == null || request.getSeats() <= 0) {
            throw new IllegalArgumentException("Invalid seat count");
        }

        if (flight.getAvailableSeats() < request.getSeats()) {
            throw new IllegalStateException("Not enough seats available");
        }

        // Update flight availability
        flight.setAvailableSeats(flight.getAvailableSeats() - request.getSeats());
        flightRepository.save(flight);

        // Create booking
        Booking booking = new Booking();
        booking.setBookingTime(LocalDateTime.now());
        booking.setJourneyDate(flight.getDepartureTime());
        booking.setSeatsBooked(request.getSeats());
        booking.setUserName(request.getUserName());
        booking.setEmail(request.getEmail());
        booking.setFlight(flight);
        booking.setCancelled(false);
        booking.setPnr(PNRGenerator.generatePNR(flight.getFlightNumber()));

        // Map passengers
        List<Passenger> passengers = request.getPassengers().stream().map(pDto -> {
            Passenger p = new Passenger();
            p.setName(pDto.getName());
            p.setAge(pDto.getAge());
            p.setGender(pDto.getGender());
            p.setMeal(pDto.getMeal());
            p.setSeatNumber(pDto.getSeatNumber());
            p.setBooking(booking);
            return p;
        }).collect(Collectors.toList());

        booking.setPassengers(passengers);

        // Calculate total amount
        booking.setTotalAmount(flight.getPrice() * request.getSeats());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingByPnr(String pnr) {
        return bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new IllegalArgumentException("PNR not found: " + pnr));
    }

    @Override
    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByEmailOrderByBookingTimeDesc(email);
    }

    @Override
    @Transactional
    public void cancelBooking(String pnr, String requestedByEmail) {
        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new IllegalArgumentException("PNR not found: " + pnr));

        if (booking.isCancelled()) {
            throw new IllegalStateException("Booking already cancelled");
        }

        LocalDateTime now = LocalDateTime.now();

        // Only the user who booked can cancel
        if (!booking.getEmail().equalsIgnoreCase(requestedByEmail)) {
            throw new SecurityException("You can only cancel your own booking");
        }

        // Check 24 hours before departure
        if (booking.getJourneyDate().isBefore(now.plusHours(24))) {
            throw new IllegalStateException("Cannot cancel: less than 24 hours to journey");
        }

        // Cancel booking
        booking.setCancelled(true);

        // Restore flight seats
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getSeatsBooked());
        flightRepository.save(flight);

        bookingRepository.save(booking);
    }
}
