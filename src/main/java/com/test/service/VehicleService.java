package com.test.service;

import com.test.dto.AddDriverDto;
import com.test.dto.VehicleDto;
import com.test.security.jwtService.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleService {

    VehicleDto createVehicle(VehicleDto vehicleDto, UserDetailsImpl currentUser);

    List<VehicleDto> getAllVehicleByAdmin(UserDetailsImpl currentUser);


    VehicleDto updateVehicleByID(Integer id,VehicleDto vehicleDto);

    void deleteVehicleById(Integer id);

    String addDriverToVehicle(AddDriverDto addDriverDto);

    ResponseEntity<?> allUserByVehicleId(Integer vehicleId);
}
