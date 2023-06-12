package com.test.controller;

import com.test.dto.ApiResponse;
import com.test.dto.AddDriverDto;
import com.test.dto.VehicleDto;
import com.test.security.jwtService.CurrentUser;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {


    private final VehicleService vehicleService;


    @GetMapping("/getAllVehicle")
    public ResponseEntity<List<VehicleDto>> getAllVehicleOfAdmin(@CurrentUser UserDetailsImpl userDetails) {
        List<VehicleDto> allVehicleByAdmin = vehicleService.getAllVehicleByAdmin(userDetails);
        return ResponseEntity.ok().body(allVehicleByAdmin);
    }

    @PostMapping("/addVehicle")
    public ResponseEntity<VehicleDto> createVehicle(@Valid @RequestBody VehicleDto vehicleDto, @CurrentUser UserDetailsImpl currentUser) {
        VehicleDto vehicle = vehicleService.createVehicle(vehicleDto, currentUser);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }


    @PutMapping("/updateVehicle/{id}")
    public ResponseEntity<VehicleDto> updateVehicleById(@PathVariable Integer id, @Valid @RequestBody  VehicleDto vehicleDto) {
        VehicleDto vehicleUpdated = vehicleService.updateVehicleByID(id, vehicleDto);
        return ResponseEntity.ok().body(vehicleUpdated);
    }

    @DeleteMapping("/deleteVehicleById/{id}")
    public ResponseEntity<ApiResponse> updateVehicleById(@PathVariable Integer id) {
        System.out.println("del");
        vehicleService.deleteVehicleById(id);
        return ResponseEntity.ok().body(new ApiResponse(Instant.now(), "Vehicle deleted successfully!", "/deleteVehicleById/" + id));
    }

    @PostMapping("/addDriver")
    public ResponseEntity<ApiResponse> addDriver(@Valid @RequestBody AddDriverDto addDriverDto) {
        String driver = vehicleService.addDriverToVehicle(addDriverDto);
        return ResponseEntity.ok().body(new ApiResponse(Instant.now(), driver, "/addDriver "));
    }

    @GetMapping("allUserOfVehicle/{vehicleId}")
    public ResponseEntity<?> allUserOfVehicle( @CurrentUser UserDetailsImpl currentUser,@PathVariable Integer vehicleId){
        return vehicleService.allUserByVehicleId(currentUser,vehicleId);
    }


}
