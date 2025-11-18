package com.flightapp.config;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;

    public DataLoader(AirlineRepository airlineRepository, FlightRepository flightRepository) {
        this.airlineRepository = airlineRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (airlineRepository.count() == 0) {
            Airline ai = airlineRepository.save(new Airline(null, "Air India", "AI", null));
            Airline ind = airlineRepository.save(new Airline(null, "IndiGo", "6E", null));

            Flight f1 = new Flight(null, "AI101", "Hyderabad", "Delhi",
                    LocalDateTime.of(2025,11,20,8,0),
                    LocalDateTime.of(2025,11,20,10,30),
                    150, 150, 6500.0, ai);
            Flight f2 = new Flight(null, "6E202", "Hyderabad", "Delhi",
                    LocalDateTime.of(2025,11,20,12,0),
                    LocalDateTime.of(2025,11,20,14,30),
                    180, 180, 5500.0, ind);
            flightRepository.save(f1);
            flightRepository.save(f2);
        }
    }
}
