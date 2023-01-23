package com.test.mapper;

import com.test.dto.UsersDto;
import com.test.dto.VehicleDto;
import com.test.entity.RoleName;
import com.test.entity.Vehicle;

public class VehicleMapper {


    public static VehicleDto vehicleToDto(Vehicle vehicle){
        VehicleDto vehicleDto = new VehicleDto()
                .setId(vehicle.getId())
                //to do .setDrivers(vehicle.getDrivers())
                //.setCarAdministrator((UsersDto) vehicle.getDrivers().stream().map(driver -> driver.getUserID()))
                .setTypeOfVehicle(vehicle.getTypeOfVehicle())
                .setVehicleName(vehicle.getVehicleName())
                .setVehicleNumber(vehicle.getVehicleNumber())
                .setYear(vehicle.getYear());
        return vehicleDto;
    }
}
