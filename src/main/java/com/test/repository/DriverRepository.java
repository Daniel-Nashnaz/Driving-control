package com.test.repository;

import com.test.entity.Driver;
import com.test.entity.UserVsAdmin;
import com.test.entity.Users;
import com.test.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    @Query("SELECT d FROM Driver d  JOIN FETCH d.userID u  JOIN FETCH d.vehicleID v " +
            "WHERE d.vehicleID.id = :vehicleID and v.isDeleted = false and v.carAdministrator.id = :adminID")
    List<Driver> findUsersByVehicleID(@Param("vehicleID") Integer vehicleID,@Param("adminID") Integer adminID);

    boolean existsDriverByVehicleIDAndUserID(Vehicle vehicle , Users user);


}