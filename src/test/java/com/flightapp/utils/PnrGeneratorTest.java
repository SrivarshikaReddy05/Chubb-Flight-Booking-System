package com.flightapp.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PnrGeneratorTest {

    @Test
    void testPnrNotNull() {
        String pnr = PNRGenerator.generatePNR("AI101");
        assertNotNull(pnr);
    }

    @Test
    void testPnrContainsFlightNumber() {
        String pnr = PNRGenerator.generatePNR("AI101");
        assertTrue(pnr.startsWith("AI101-"));
    }

    @Test
    void testPnrFormat() {
        String pnr = PNRGenerator.generatePNR("AI101");
        assertTrue(pnr.matches("AI101-[0-9]{6,}[0-9]{3}"));
    }
}
