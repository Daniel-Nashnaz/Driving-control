package com.test.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UsersAllowSendingMessageDto implements Serializable {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer alertLevel;
    @NotNull
    private Boolean reports;
    @NotNull
    private Boolean exceededSpeedLimit;
    @NotNull
    private Boolean forwardDirections;
    @NotNull
    private Boolean laneDeparture;
    @NotNull
    private Boolean pedestrianAndCyclistCollision;
    @NotNull
    private Boolean suddenBraking;
}