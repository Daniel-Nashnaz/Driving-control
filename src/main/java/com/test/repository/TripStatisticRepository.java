package com.test.repository;

import com.test.dto.AllStatisticsDTO;
import com.test.entity.TripStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TripStatisticRepository extends JpaRepository<TripStatistic, Integer> {
    @Query("SELECT new com.test.dto.AllStatisticsDTO(ts.id, ts.tripID.id, ts.duration, ts.distanceTraveledMile, " +
            "ts.averageSpeed, ts.numTimesExceededSpeedLimit, ts.maxSpeed, " +
            "ts.forwardWarningDirectionsCount, ts.laneDepartureWarningCount, " +
            "ts.pedestrianAndCyclistCollisionWarningCount, ts.suddenBrakingCount, " +
            "ms.numLeftLaneDeparture, ms.numRightLaneDeparture, ms.numForwardWarningDirectionsUp, " +
            "ms.numForwardWarningDirectionsLeft, ms.numForwardWarningDirectionsRight, ms.tripScore) " +
            "FROM TripStatistic ts JOIN MoreInfoAboutStatistic ms ON ts.tripID.id = ms.tripID.id WHERE ts.tripID.id =:tripId")
    Optional<AllStatisticsDTO> findAllWithMoreInfo(@Param("tripId") Integer tripId);

}