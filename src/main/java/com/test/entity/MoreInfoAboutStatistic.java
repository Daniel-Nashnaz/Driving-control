package com.test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MoreInfoAboutStatistics")
public class MoreInfoAboutStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TripID", nullable = false)
    @JsonBackReference
    private Travel tripID;

    @NotNull
    @Column(name = "NumLeftLaneDeparture", nullable = false)
    private Integer numLeftLaneDeparture;

    @NotNull
    @Column(name = "NumRightLaneDeparture", nullable = false)
    private Integer numRightLaneDeparture;

    @NotNull
    @Column(name = "NumForwardWarningDirectionsUp", nullable = false)
    private Integer numForwardWarningDirectionsUp;

    @NotNull
    @Column(name = "NumForwardWarningDirectionsLeft", nullable = false)
    private Integer numForwardWarningDirectionsLeft;

    @NotNull
    @Column(name = "NumForwardWarningDirectionsRight", nullable = false)
    private Integer numForwardWarningDirectionsRight;

    @Column(name = "TripScore")
    private Double tripScore;

}