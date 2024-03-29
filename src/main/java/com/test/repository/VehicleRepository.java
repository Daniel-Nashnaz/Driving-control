package com.test.repository;

import com.test.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByCarAdministratorIdAndIsDeletedIsFalse(Integer id);

    Integer countByCarAdministratorIdAndIsDeletedIsFalse(Integer id);
    Optional<Vehicle> findByIdAndIsDeletedIsFalse(Integer id);


    Optional<Vehicle> findVehicleByVehicleNumberAndIsDeletedIsFalse(String vehicleNumber);
}