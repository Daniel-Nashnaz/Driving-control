package com.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
public class RealTimeInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TripID", nullable = false)
    private Travel tripID;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "TimeFromStart", nullable = false, length = 20)
    private String timeFromStart;

    @NotNull
    @Column(name = "Latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "Longitude", nullable = false)
    private Double longitude;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "ForwardWarningDirections", nullable = false, length = 20)
    private String forwardWarningDirections;

    @Size(max = 10)
    @NotNull
    @Nationalized
    @Column(name = "ForwardWarningDistance", nullable = false, length = 10)
    private String forwardWarningDistance;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "LaneDepartureWarning", nullable = false, length = 20)
    private String laneDepartureWarning;

    @Size(max = 10)
    @NotNull
    @Nationalized
    @Column(name = "PedestrianAndCyclistCollisionWarning", nullable = false, length = 10)
    private String pedestrianAndCyclistCollisionWarning;

    @NotNull
    @Column(name = "SuddenBraking", nullable = false)
    private Boolean suddenBraking = false;

    @NotNull
    @Column(name = "SpeedAllowed", nullable = false)
    private Integer speedAllowed;

    @NotNull
    @Column(name = "CurrentSpeed", nullable = false)
    private Integer currentSpeed;

    @Column(name = "DistanceTraveledMile")
    private Double distanceTraveledMile;

}