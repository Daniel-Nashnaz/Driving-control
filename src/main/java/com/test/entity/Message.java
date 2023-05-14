package com.test.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    @JsonBackReference
    private Users userID;

    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "Subject", nullable = false, length = 100)
    private String subject;

    @NotNull
    @Nationalized
    @Lob
    @Column(name = "Body", nullable = false)
    private String body;

    @Column(name = "SentTime")
    private Instant sentTime;

}