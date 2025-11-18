package com.flightapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    @NotBlank
    private String userName;

    @Email @NotBlank
    private String email;

    @Min(1)
    private Integer seats;

    @NotEmpty
    private List<PassengerDto> passengers;
}
