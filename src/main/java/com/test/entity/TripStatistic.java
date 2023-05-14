package com.test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "TripStatistics")
public class TripStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TripID", nullable = false)
    @JsonBackReference
    private Travel tripID;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "Duration", nullable = false, length = 20)
    private String duration;

    @NotNull
    @Column(name = "DistanceTraveledMile", nullable = false, precision = 18, scale = 2)
    private BigDecimal distanceTraveledMile;

    @NotNull
    @Column(name = "AverageSpeed", nullable = false, precision = 18, scale = 2)
    private BigDecimal averageSpeed;

    @NotNull
    @Column(name = "NumTimesExceededSpeedLimit", nullable = false)
    private Integer numTimesExceededSpeedLimit;

    @NotNull
    @Column(name = "MaxSpeed", nullable = false)
    private Integer maxSpeed;

    @NotNull
    @Column(name = "ForwardWarningDirectionsCount", nullable = false)
    private Integer forwardWarningDirectionsCount;

    @NotNull
    @Column(name = "LaneDepartureWarningCount", nullable = false)
    private Integer laneDepartureWarningCount;

    @NotNull
    @Column(name = "PedestrianAndCyclistCollisionWarningCount", nullable = false)
    private Integer pedestrianAndCyclistCollisionWarningCount;

    @NotNull
    @Column(name = "SuddenBrakingCount", nullable = false)
    private Integer suddenBrakingCount;

}