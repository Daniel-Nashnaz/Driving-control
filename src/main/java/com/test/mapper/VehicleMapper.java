package com.test.mapper;

import com.test.dto.UsersDto;
import com.test.dto.VehicleDto;
import com.test.entity.Driver;
import com.test.entity.RoleName;
import com.test.entity.Users;
import com.test.entity.Vehicle;
import com.test.repository.DriverRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class VehicleMapper {

    private final DriverRepository driverRepository;


    public static VehicleDto vehicleToDto(Vehicle vehicle) {


        VehicleDto vehicleDto = new VehicleDto()
                .setId(vehicle.getId())
                //to do .setDrivers(vehicle.getDrivers())
                .setCarAdministrator(vehicle.getCarAdministrator().getId())
                .setTypeOfVehicle(vehicle.getTypeOfVehicle())
                .setVehicleName(vehicle.getVehicleName())
                .setVehicleNumber(vehicle.getVehicleNumber())
                .setYear(vehicle.getYear());

//        Optional<Driver> byVehicleID = driverRepository.findByVehicleID(vehicle.getId());
//
//        byVehicleID.get().getUserID()
        return vehicleDto;
    }

    public static Vehicle DtoToVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(vehicleDto.getVehicleName());
        vehicle.setVehicleNumber(vehicleDto.getVehicleNumber());
        vehicle.setTypeOfVehicle(vehicleDto.getTypeOfVehicle());
        vehicle.setYear(vehicleDto.getYear());
        return vehicle;

    }


}
