package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.test.entity.Driver} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DriverDto implements Serializable {
    private Integer id;
    @NotNull
    private UsersDto userID;
    @NotNull
    private VehicleDto vehicleID;
    @NotNull
    private Instant travelStart;
    private Instant travelEnd;
}