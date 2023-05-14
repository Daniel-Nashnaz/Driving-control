package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class TripsOfUserDto {

    @NotNull
    private Integer tripId;
    @NotNull
    private int userId;
    @NotNull
    private int vehicleId;

    @NotNull
    private Instant travelStart;
    @NotNull
    private Instant travelEnd;
    @NotNull
    private String typeOfVehicle;
    @NotNull
    private String vehicleName;
    @NotNull
    private String vehicleNumber;
    @NotNull
    private double tripScore;
}
