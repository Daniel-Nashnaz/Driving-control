package com.test.repository;

import com.test.dto.TripSummaryDto;
import com.test.dto.TripsOfUserDto;
import com.test.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
public interface TravelRepository extends JpaRepository<Travel, Integer> {
    @Query("SELECT new com.test.dto.TripsOfUserDto(t.id, t.userID.id, t.vehicleID.id, t.travelStart, t.travelEnd, v.typeOfVehicle, v.vehicleName, v.vehicleNumber, s.tripScore) " +
            "FROM MoreInfoAboutStatistic s " +
            "INNER JOIN s.tripID t " +
            "INNER JOIN t.userID u " +
            "INNER JOIN t.vehicleID v " +
            "WHERE u.id = :userId AND t.travelEnd IS NOT NULL " +
            "ORDER BY t.id DESC")
    List<TripsOfUserDto> findLatestTripsByUser(@Param("userId") Integer userId);

    @Query(value = "EXEC GetTripDetails @Date = :date, @UserID = :userId", nativeQuery = true)
    List<TripSummaryDto> getTripDetails(@Param("date") LocalDateTime date, @Param("userId") int userId);
    @Query("SELECT NEW com.test.dto.TripSummaryDto(" +
            "ts.tripID.id, " +
            "ts.duration, " +
            "ts.distanceTraveledMile, " +
            "ts.averageSpeed, " +
            "ts.numTimesExceededSpeedLimit, " +
            "ts.maxSpeed, " +
            "ts.forwardWarningDirectionsCount, " +
            "ts.laneDepartureWarningCount, " +
            "ts.pedestrianAndCyclistCollisionWarningCount, " +
            "ms.numLeftLaneDeparture, " +
            "ms.numRightLaneDeparture, " +
            "ms.numForwardWarningDirectionsUp, " +
            "ms.numForwardWarningDirectionsLeft, " +
            "ms.numForwardWarningDirectionsRight, " +
            "ms.tripScore, " +
            "t.userID.id, " +
            "t.vehicleID.id, " +
            "t.travelStart, " +
            "t.travelEnd) " +
            "FROM TripStatistic ts " +
            "JOIN MoreInfoAboutStatistic ms ON ts.tripID = ms.tripID " +
            "JOIN Travel t ON ts.tripID.id = t.id " +
            "WHERE t.userID.id = :userID " +
            "AND t.travelStart >= :startDate " +
            "AND t.travelEnd is not null ")
    List<TripSummaryDto> getTripStatisticsByUserIDAndStartDate(@Param("userID") Integer userID, @Param("startDate") Instant startDate);


    @Procedure(procedureName = "[dbo].[GetAllAvgrageScoresOfDrivers]")
    @Transactional(readOnly = true )
    List<Object[]> getUserTripAverages(@Param("adminId") int adminId);

   @Query("SELECT COUNT(*) " +
           "FROM Travel t JOIN UserVsAdmin uva " +
           "ON t.userID.id = uva.userID.id " +
           "where uva.administratorID.id = :adminId ")
    Integer countTripsByAdminId(@Param("adminId") int adminId);



}

