package com.test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AllStatisticsDTO {
    private Integer id;
    private Integer tripId;
    @NotNull
    @Size(max = 20)
    private String duration;
    @NotNull
    private BigDecimal distanceTraveledMile;
    @NotNull
    private BigDecimal averageSpeed;
    @NotNull
    private Integer numTimesExceededSpeedLimit;
    @NotNull
    private Integer maxSpeed;
    @NotNull
    private Integer forwardWarningDirectionsCount;
    @NotNull
    private Integer laneDepartureWarningCount;
    @NotNull
    private Integer pedestrianAndCyclistCollisionWarningCount;
    @NotNull
    private Integer suddenBrakingCount;
    @NotNull
    private Integer numLeftLaneDeparture;
    @NotNull
    private Integer numRightLaneDeparture;
    @NotNull
    private Integer numForwardWarningDirectionsUp;
    @NotNull
    private Integer numForwardWarningDirectionsLeft;
    @NotNull
    private Integer numForwardWarningDirectionsRight;
    private Double tripScore;


}

