package com.flightapp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PNRGenerator {
    public static String generatePNR(String flightNumber) {
        String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int r = new Random().nextInt(900) + 100;
        return flightNumber + "-" + timePart.substring(8) + r;
    }
}
