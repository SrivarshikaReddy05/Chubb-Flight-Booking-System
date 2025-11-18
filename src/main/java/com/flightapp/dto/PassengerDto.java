package com.flightapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
	
    @NotBlank
    private String name;

    @NotBlank
    private String gender;

    @NotNull
    @Min(0)
    private Integer age;

    private String seatNumber;

    private String meal;
}
