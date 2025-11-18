package com.flightapp.controller;

import com.flightapp.service.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Test
    void testSearchFlights_OneWay() throws Exception {

        Mockito.when(flightService.searchFlights(Mockito.any()))
                .thenReturn(Collections.emptyList());

        String requestJson = """
            {
                "fromPlace":"HYD",
                "toPlace":"DEL",
                "journeyDate":"2025-11-20",
                "roundTrip": false
            }
        """;

        mockMvc.perform(post("/api/v1.0/flight/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchFlights_RoundTrip() throws Exception {

        Mockito.when(flightService.searchFlights(Mockito.any()))
                .thenReturn(Collections.emptyList());

        String requestJson = """
            {
                "fromPlace":"HYD",
                "toPlace":"DEL",
                "journeyDate":"2025-11-20",
                "returnDate":"2025-11-25",
                "roundTrip": true
            }
        """;

        mockMvc.perform(post("/api/v1.0/flight/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchFlights_NoResults() throws Exception {

        Mockito.when(flightService.searchFlights(Mockito.any()))
                .thenReturn(Collections.emptyList());

        String requestJson = """
            {
                "fromPlace":"Hyd",
                "toPlace":"Chennai",
                "journeyDate":"2025-11-20",
                "roundTrip": false
            }
        """;

        mockMvc.perform(post("/api/v1.0/flight/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }
}
