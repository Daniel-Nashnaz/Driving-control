package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticsOfUserDto {

    @NotNull
    private BigDecimal averageSpeed;
    @NotNull
    private Integer totalNumTimesExceededSpeedLimit;
    @NotNull
    private Integer totalPedestrianAndCyclistCollisionWarningCount;
    @NotNull
    private Integer totalSuddenBrakingCount;
    @NotNull
    private Integer totalNumLeftLaneDeparture;
    @NotNull
    private Integer totalNumRightLaneDeparture;
    @NotNull
    private Integer totalNumForwardWarningDirectionsUp;
    @NotNull
    private Integer totalNumForwardWarningDirectionsLeft;
    @NotNull
    private Integer totalNumForwardWarningDirectionsRight;
    private Double AverageTripScore;


}


