package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class TripSummaryDto implements Serializable{
    @NotNull
    private int tripId;
    @NotNull
    private int userId;
    @NotNull
    private int vehicleId;
    @NotNull
    private LocalDateTime  travelStart;
    @NotNull
    private LocalDateTime travelEnd;
    @NotNull
    private String duration;
    @NotNull
    private double distanceTraveledMile;
    @NotNull
    private double averageSpeed;
    @NotNull
    private int numTimesExceededSpeedLimit;
    @NotNull
    private int maxSpeed;
    @NotNull
    private int forwardWarningDirectionsCount;
    @NotNull
    private int laneDepartureWarningCount;
    @NotNull
    private int pedestrianAndCyclistCollisionWarningCount;
    @NotNull
    private int suddenBrakingCount;
    @NotNull
    private double tripScore;

}
