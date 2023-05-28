package com.test.mapper;

import com.test.dto.UserTripAverageDto;

import java.util.ArrayList;
import java.util.List;

public class StatisticMapper {

    public static List<UserTripAverageDto> mapToUserTripAverages(List<Object[]> rows) {
        List<UserTripAverageDto> averages = new ArrayList<>();
        for (Object[] row : rows) {
            Integer userId = (Integer) row[0];
            Double avgScores = (Double) row[1];
            String fullName = (String) row[2];

            UserTripAverageDto average = new UserTripAverageDto(userId, avgScores, fullName);
            averages.add(average);
        }
        return averages;
    }
}
