package com.test.repository;

import com.test.dto.TripsOfUserDto;
import com.test.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Integer> {
    @Query("SELECT new com.test.dto.TripsOfUserDto(t.id, t.userID.id, t.vehicleID.id, t.travelStart, t.travelEnd, v.typeOfVehicle, v.vehicleName, v.vehicleNumber, s.tripScore) " +
            "FROM MoreInfoAboutStatistic s " +
            "INNER JOIN s.tripID t " +
            "INNER JOIN t.userID u " +
            "INNER JOIN t.vehicleID v " +
            "WHERE u.id = :userId " +
            "ORDER BY t.id DESC")
    List<TripsOfUserDto> findLatestTripsByUser(@Param("userId") Integer userId);
}

