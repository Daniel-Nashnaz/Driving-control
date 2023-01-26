package com.test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.test.entity.Vehicle} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleDto implements Serializable {
    private Integer id;
    private Integer carAdministrator;
    private Set<DriversDto> drivers = new LinkedHashSet<>();

    @Size(min = 2,max = 10, message = "Vehicle number should have at bigger 2 characters and least 10 characters")
    @NotNull(message = "You must fill in the number of the vehicle")
    private String vehicleNumber;
    @Size(min = 2,max = 20 , message = "Vehicle name should have at bigger 2 characters and least 20 characters")
    @NotNull(message = "You must fill in the name of the vehicle")
    private String vehicleName;
    @Size(min = 2,max = 20 , message = "Vehicle name should have at bigger 2 characters and least 20 characters")
    @NotNull(message = "You must fill in the type of the vehicle")
    private String typeOfVehicle;
    @NotNull(message = "You must be a year")
    private Integer year;
}