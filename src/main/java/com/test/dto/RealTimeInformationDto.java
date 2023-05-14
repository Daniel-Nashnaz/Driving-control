package com.test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RealTimeInformationDto implements Serializable {
    private Long id;
    @NotNull
    private Integer tripID;
    @Size(max = 20)
    @NotNull
    private String timeFromStart;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @Size(max = 20)
    @NotNull
    private String forwardWarningDirections;
    @Size(max = 10)
    @NotNull
    private String forwardWarningDistance;
    @Size(max = 20)
    @NotNull
    private String laneDepartureWarning;
    @Size(max = 10)
    @NotNull
    private String pedestrianAndCyclistCollisionWarning;
    @NotNull
    private Boolean suddenBraking;
    @NotNull
    private Integer speedAllowed;
    @NotNull
    private Integer currentSpeed;
    private Double distanceTraveledMile;


}