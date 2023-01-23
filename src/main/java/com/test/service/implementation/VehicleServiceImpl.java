package com.test.service.implementation;

import com.test.dto.VehicleDto;
import com.test.entity.Users;
import com.test.entity.Vehicle;
import com.test.mapper.VehicleMapper;
import com.test.repository.UserRepository;
import com.test.repository.VehicleRepository;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto, UserDetailsImpl currentUser) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleDto.getVehicleNumber());
        vehicle.setVehicleName(vehicleDto.getVehicleName());
        vehicle.setTypeOfVehicle(vehicleDto.getTypeOfVehicle());
        vehicle.setYear(vehicleDto.getYear());
        Optional<Users> users = userRepository.findById(currentUser.getId());
        vehicle.setCarAdministrator(users.get());
        Vehicle vehicleSaved = vehicleRepository.save(vehicle);
        return VehicleMapper.vehicleToDto(vehicleSaved);
    }
}
