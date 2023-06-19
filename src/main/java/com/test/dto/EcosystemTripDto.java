package com.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EcosystemTripDto {

        private double latitude;
        private double longitude;
        private String ecologicalType;
        private int currentSpeed;
        private double distanceTraveledMile;

}
