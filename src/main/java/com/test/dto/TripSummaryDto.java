package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
@Data
@Accessors(chain = true)
public class TripSummaryDto implements Serializable{
    @NotNull
    private int tripID;
    @NotNull

    private String duration;
    @NotNull
    private BigDecimal distanceTraveledMile;
    @NotNull
    private BigDecimal averageSpeed;
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
    private int numLeftLaneDeparture;
    @NotNull
    private int numRightLaneDeparture;
    @NotNull
    private int numForwardWarningDirectionsUp;
    @NotNull
    private int numForwardWarningDirectionsLeft;
    @NotNull
    private int numForwardWarningDirectionsRight;
    @NotNull
    private double tripScore;
    @NotNull
    private int userID;
    @NotNull
    private int vehicleID;
    @NotNull
    private Instant travelEnd;
    @NotNull
    private Instant travelStart;


    public TripSummaryDto(int tripID, String duration, BigDecimal distanceTraveledMile, BigDecimal averageSpeed, int numTimesExceededSpeedLimit, int maxSpeed, int forwardWarningDirectionsCount,
                          int laneDepartureWarningCount, int pedestrianAndCyclistCollisionWarningCount,
                          int numLeftLaneDeparture, int numRightLaneDeparture, int numForwardWarningDirectionsUp,
                          int numForwardWarningDirectionsLeft, int numForwardWarningDirectionsRight, double tripScore,
                          int userID, int vehicleID, Instant travelEnd, Instant travelStart) {
        this.tripID = tripID;
        this.duration = duration;
        this.distanceTraveledMile = distanceTraveledMile;
        this.averageSpeed = averageSpeed;
        this.numTimesExceededSpeedLimit = numTimesExceededSpeedLimit;
        this.maxSpeed = maxSpeed;
        this.forwardWarningDirectionsCount = forwardWarningDirectionsCount;
        this.laneDepartureWarningCount = laneDepartureWarningCount;
        this.pedestrianAndCyclistCollisionWarningCount = pedestrianAndCyclistCollisionWarningCount;
        this.numLeftLaneDeparture = numLeftLaneDeparture;
        this.numRightLaneDeparture = numRightLaneDeparture;
        this.numForwardWarningDirectionsUp = numForwardWarningDirectionsUp;
        this.numForwardWarningDirectionsLeft = numForwardWarningDirectionsLeft;
        this.numForwardWarningDirectionsRight = numForwardWarningDirectionsRight;
        this.tripScore = tripScore;
        this.userID = userID;
        this.vehicleID = vehicleID;
        this.travelEnd = travelEnd;
        this.travelStart = travelStart;
    }
}
