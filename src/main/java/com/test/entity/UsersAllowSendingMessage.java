package com.test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "UsersAllowSendingMessages")
public class UsersAllowSendingMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    //this is Admin ID.
    private Users userID;

    @NotNull
    @Column(name = "AlertLevel", nullable = false)
    private Integer alertLevel;

    @NotNull
    @Column(name = "ExceededSpeedLimit", nullable = false)
    private Boolean exceededSpeedLimit = false;

    @NotNull
    @Column(name = "ForwardDirections", nullable = false)
    private Boolean forwardDirections = false;

    @NotNull
    @Column(name = "LaneDeparture", nullable = false)
    private Boolean laneDeparture = false;

    @NotNull
    @Column(name = "PedestrianAndCyclistCollision", nullable = false)
    private Boolean pedestrianAndCyclistCollision = false;

    @NotNull
    @Column(name = "SuddenBraking", nullable = false)
    private Boolean suddenBraking = false;

    @NotNull
    @Column(name = "Reports", nullable = false)
    private Boolean reports = false;

}