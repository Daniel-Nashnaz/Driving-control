package com.test.mapper;

import com.test.dto.AllScoresOfUserDto;
import com.test.dto.StatisticsOfUserDto;
import com.test.dto.UserTripAverageDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatisticMapper {

    public static List<UserTripAverageDto> mapToUserTripAverages(List<Object[]> rows) {
        ArrayList<UserTripAverageDto> averages = new ArrayList<>();
        for (Object[] row : rows) {
            Integer userId = (Integer) row[0];
            Double avgScores = (Double) row[1];
            String fullName = (String) row[2];

            UserTripAverageDto average = new UserTripAverageDto(userId, avgScores, fullName);
            averages.add(average);


        }
        return averages;
    }

    public static List<StatisticsOfUserDto> mapToStatisticOfUser(List<Object[]> rows) {
        ArrayList<StatisticsOfUserDto> statistics = new ArrayList<>();
        Object[] row = rows.get(0);
        StatisticsOfUserDto tripStatistics = new StatisticsOfUserDto();
        tripStatistics.setTotalNumLeftLaneDeparture((int) row[0]);
        tripStatistics.setTotalNumRightLaneDeparture((int) row[1]);
        tripStatistics.setTotalNumForwardWarningDirectionsUp((int) row[2]);
        tripStatistics.setTotalNumForwardWarningDirectionsLeft((int) row[3]);
        tripStatistics.setTotalNumForwardWarningDirectionsRight((int) row[4]);
        tripStatistics.setTotalNumTimesExceededSpeedLimit((int) row[5]);
        tripStatistics.setTotalPedestrianAndCyclistCollisionWarningCount((int) row[6]);
        tripStatistics.setTotalSuddenBrakingCount((int) row[7]);
        tripStatistics.setAverageTripScore((Double) row[8]);
        tripStatistics.setAverageSpeed((BigDecimal) row[9]);
        statistics.add(tripStatistics);

        return statistics;

    }

    public static List<AllScoresOfUserDto> mapToScoresOfUser(List<Object[]> rows) {
        ArrayList<AllScoresOfUserDto> scores = new ArrayList<>();
        for (Object[] row : rows) {
            AllScoresOfUserDto allScoresOfUser = new AllScoresOfUserDto()
                    .setTripId((Integer) row[0])
                    .setTripScore((double) row[1])
                    .setTripStart( row[2]);
            scores.add(allScoresOfUser);
        }

        return scores;

    }
}
