package com.test.service.implementation;

import com.test.dto.StatisticsOfUserDto;
import com.test.dto.UserTripAverageDto;
import com.test.mapper.StatisticMapper;
import com.test.repository.*;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.test.service.implementation.ConstantsOfReport.*;


@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final TravelRepository travelRepository;

    private final ActionsServiceImpl actionsService;

    private final MoreInfoAboutStatisticRepository moreInfoAboutStatisticRepository;
    private final UserVsAdminRepository userVsAdminRepository;
    private final VehicleRepository vehicleRepository;
    private final TripStatisticRepository tripStatisticRepository;

    @Override
    public String getAllData(UserDetailsImpl userDetails) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scores", getAllScoresOfUsers(userDetails));
        jsonObject.put("avgAll", moreInfoAboutStatisticRepository.averageOfAllDriving(userDetails.getId()));
        jsonObject.put("allDrivers", userVsAdminRepository.countUsersByAdministratorId(userDetails.getId()));
        jsonObject.put("allTrip", travelRepository.countTripsByAdminId(userDetails.getId()));
        jsonObject.put("allVehicle", vehicleRepository.countByCarAdministratorIdAndIsDeletedIsFalse(userDetails.getId()));

        return jsonObject.toString();
    }


    @Override
    public String getAllStatistic(UserDetailsImpl userDetails, Integer userId) {

        JSONObject jsonObject = new JSONObject();
        List<StatisticsOfUserDto> allStatistic = getAllStatisticOfUserId(userId);
        jsonObject.putOnce("allStatistic", allStatistic);
        jsonObject.put("report", createReport(allStatistic.get(0)));
        jsonObject.put("countTrips", travelRepository.countByUserID_Id(userId));
        jsonObject.put("level", actionsService.getMessageSendSettingsOfAdminId(userDetails.getId()));
        return jsonObject.toString();

    }

    public List<UserTripAverageDto> getAllScoresOfUsers(UserDetailsImpl userDetails) {
        List<Object[]> userTripAverages = travelRepository.getUserTripAverages(userDetails.getId());
        return StatisticMapper.mapToUserTripAverages(userTripAverages);
    }

    public List<StatisticsOfUserDto> getAllStatisticOfUserId(int userId) {
        List<Object[]> allStatisticOfUserId = tripStatisticRepository.getAllStatisticOfUserId(userId);
        return StatisticMapper.mapToStatisticOfUser(allStatisticOfUserId);
    }

    public StringBuilder createReport(StatisticsOfUserDto statisticsOfUser) {

        StringBuilder report = new StringBuilder();

        report.append("<h5>Driver notes report</h5>");
        report.append("<h6>");
        report.append("Sudden Braking: " + getAlertOfSuddenBraking(statisticsOfUser.getTotalSuddenBrakingCount()) + "<br>");
        report.append("Pedestrian and Cyclist Collision: " + getAlertOfPedestrianAndCyclistCollision(statisticsOfUser.getTotalPedestrianAndCyclistCollisionWarningCount()) + "<br>");
        report.append("Exceeded Speed: " + getAlertOfExceededSpeed(statisticsOfUser.getTotalNumTimesExceededSpeedLimit()) + "<br>");
        report.append("Lane Departure: " + getAlertOfLaneDeparture((statisticsOfUser.getTotalNumLeftLaneDeparture() + statisticsOfUser.getTotalNumRightLaneDeparture())) + "<br>");
        report.append("Forward Directions: " + getAlertOfForwardDirections((statisticsOfUser.getTotalNumForwardWarningDirectionsUp() + statisticsOfUser.getTotalNumForwardWarningDirectionsLeft()) + statisticsOfUser.getTotalNumForwardWarningDirectionsRight()) + "<br>");
        report.append("</h6>");
        report.append("<h5>Summary:</h5> " + END_REPORT);
        return report;

    }

    private String getAlertOfForwardDirections(int totalForwardDirectionsCount) {
        if (totalForwardDirectionsCount <= COUNT_LANE_DEPARTURE_WARNING_OR_FORWARD_WARNING_DIRECTIONS_GREEN) {
            return FORWARD_WARNING_DIRECTIONS_GREEN;
        } else if (totalForwardDirectionsCount >= COUNT_LANE_DEPARTURE_WARNING_OR_FORWARD_WARNING_DIRECTIONS_RED) {
            return FORWARD_WARNING_DIRECTIONS_RED;
        } else {
            return FORWARD_WARNING_DIRECTIONS_ORANGE;
        }
    }

    private String getAlertOfLaneDeparture(int totalLaneDepartureWarningCount) {
        if (totalLaneDepartureWarningCount <= COUNT_LANE_DEPARTURE_WARNING_OR_FORWARD_WARNING_DIRECTIONS_GREEN) {
            return LANE_DEPARTURE_WARNING_GREEN;
        } else if (totalLaneDepartureWarningCount >= COUNT_PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_RED) {
            return LANE_DEPARTURE_WARNING_RED;
        } else {
            return LANE_DEPARTURE_WARNING_ORANGE;
        }
    }

    private String getAlertOfExceededSpeed(Integer totalNumTimesExceededSpeedLimit) {
        if (totalNumTimesExceededSpeedLimit <= COUNT_EXCEEDED_SPEED_GREEN) {
            return EXCEEDED_SPEED_GREEN;
        } else if (totalNumTimesExceededSpeedLimit >= COUNT_EXCEEDED_SPEED_RED) {
            return EXCEEDED_SPEED_RED;
        } else {
            return EXCEEDED_SPEED_ORANGE;
        }
    }

    private String getAlertOfPedestrianAndCyclistCollision(Integer totalPedestrianAndCyclistCollisionWarningCount) {
        if (totalPedestrianAndCyclistCollisionWarningCount <= COUNT_PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_GREEN) {
            return PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_GREEN;
        } else if (totalPedestrianAndCyclistCollisionWarningCount >= COUNT_PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_RED) {
            return PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_RED;
        } else {
            return PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_ORANGE;
        }
    }

    private String getAlertOfSuddenBraking(Integer totalSuddenBrakingCount) {
        if (totalSuddenBrakingCount <= COUNT_SUDDEN_BRAKIMG_GREEN) {
            return SUDDEN_BRAKIMG_GREEN;
        } else if (totalSuddenBrakingCount >= COUNT_SUDDEN_BRAKIMG_RED) {
            return SUDDEN_BRAKIMG_RED;
        } else {
            return SUDDEN_BRAKIMG_ORANGE;
        }
    }

}
