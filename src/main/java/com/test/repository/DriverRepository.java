package com.test.repository;

import com.test.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    //add join fetch...
    @Query("select d from Driver d where d.vehicleID.id =?1")
    Optional<Driver> findByVehicleID(Integer id);




}