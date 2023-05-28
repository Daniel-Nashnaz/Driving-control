package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UserTripAverageDto {
    @NotNull
    private Integer UserID;
    @NotNull
    private double AvgScores;
    @NotNull
    private String FullName;



}
