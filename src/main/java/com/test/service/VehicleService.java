package com.test.service;

import com.test.dto.VehicleDto;
import com.test.security.jwtService.UserDetailsImpl;

import java.nio.file.attribute.UserPrincipal;

public interface VehicleService {

    VehicleDto createVehicle(VehicleDto vehicleDto, UserDetailsImpl currentUser);
}
