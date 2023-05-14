package com.test.service.implementation;

import com.test.dto.AddDriverDto;
import com.test.dto.DriversDto;
import com.test.dto.VehicleDto;
import com.test.entity.Driver;
import com.test.entity.UserVsAdmin;
import com.test.entity.Users;
import com.test.entity.Vehicle;
import com.test.exception.AuthApiException;
import com.test.mapper.VehicleMapper;
import com.test.repository.DriverRepository;
import com.test.repository.UserRepository;
import com.test.repository.VehicleRepository;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.test.mapper.VehicleMapper.*;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto, UserDetailsImpl currentUser) {
        Vehicle vehicle = DtoToVehicle(vehicleDto);
        vehicle.setIsDeleted(false);
        Optional<Users> users = userRepository.findById(currentUser.getId());
        vehicle.setCarAdministrator(users.get());
        Vehicle vehicleSaved = vehicleRepository.save(vehicle);
        return vehicleToDto(vehicleSaved);
    }

    @Override
    public List<VehicleDto> getAllVehicleByAdmin(UserDetailsImpl currentUser) {
        List<Vehicle> byCarAdministratorId = vehicleRepository.findByCarAdministratorIdAndIsDeletedIsFalse(currentUser.getId());
        return byCarAdministratorId.stream().map(vehicle -> vehicleToDto(vehicle)).collect(Collectors.toList());
    }

    @Override
    public VehicleDto updateVehicleByID(Integer id,VehicleDto vehicleDto) {
        Vehicle vehicleId = vehicleRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(()->new AuthApiException(HttpStatus.BAD_REQUEST,"vehicle not found."));
        vehicleId.setVehicleName(vehicleDto.getVehicleName());
        vehicleId.setVehicleNumber(vehicleDto.getVehicleNumber());
        vehicleId.setTypeOfVehicle(vehicleDto.getTypeOfVehicle());
        vehicleId.setYear(vehicleDto.getYear());
        Vehicle updateVehicle = vehicleRepository.save(vehicleId);
        return vehicleToDto(updateVehicle);

    }

    @Override
    public void deleteVehicleById(Integer id) {
        Vehicle vehicleDeleted = vehicleRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(()->new AuthApiException(HttpStatus.BAD_REQUEST,"vehicle not found."));
        vehicleDeleted.setIsDeleted(true);
        vehicleRepository.save(vehicleDeleted);
    }

    @Override
    public String addDriverToVehicle(AddDriverDto addDriverDto) {
        Users userOfVehicle = userRepository.findByUserNameAndIsDeletedFalseOrEmailAndIsDeletedFalse(addDriverDto.getUserNameOrEmail(), addDriverDto.getUserNameOrEmail())
                .orElseThrow(() -> new AuthApiException(HttpStatus.BAD_REQUEST, "User name or Email not exsist."));

        Vehicle vehicle = vehicleRepository.findVehicleByVehicleNumberAndIsDeletedIsFalse(addDriverDto.getVehicleNumber())
                .orElseThrow(() -> new AuthApiException(HttpStatus.BAD_REQUEST, "Vehicle not exsist"));
        if(driverRepository.existsDriverByVehicleIDAndUserID(vehicle,userOfVehicle)){
            return "Already exists!";
        }
        Driver newDriver = new Driver();
        newDriver.setUserID(userOfVehicle);
        newDriver.setVehicleID(vehicle);
        driverRepository.save(newDriver);
        return "Driver to vehicle successfully!";
    }


    @Override
    public ResponseEntity<?> allUserByVehicleId(Integer vehicleId) {
        List<Driver> usersByVehicleID = driverRepository.findUsersByVehicleID(vehicleId);
        List<DriversDto> collect = usersByVehicleID.stream().map(Driver::getUserID).map(VehicleMapper::userOfDriverToDto).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }
}
