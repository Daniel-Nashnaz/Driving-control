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
public class Ecosystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RtdiID", nullable = false)
    private RealTimeInformation rtdiID;

    @Size(max = 10)
    @NotNull
    @Nationalized
    @Column(name = "EcologicalTypes", nullable = false, length = 10)
    private String ecologicalTypes;

}