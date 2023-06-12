package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AllScoresOfUserDto {

    @NotNull
    private Integer tripId;
    @NotNull
    private double tripScore;
    @NotNull
    private Object tripStart;;


}
