package com.test.controller;

import com.test.dto.UserTripAverageDto;
import com.test.mapper.StatisticMapper;
import com.test.repository.MoreInfoAboutStatisticRepository;
import com.test.repository.TravelRepository;
import com.test.repository.UserVsAdminRepository;
import com.test.repository.VehicleRepository;
import com.test.security.jwtService.CurrentUser;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.implementation.StatisticServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticServiceImpl statisticService ;
    private final UserVsAdminRepository userVsAdminRepository;
    private final VehicleRepository vehicleRepository;
    private final TravelRepository travelRepository;
    private final MoreInfoAboutStatisticRepository moreInfoAboutStatisticRepository;


    @GetMapping("/allScore")
    @Transactional
    public ResponseEntity allScores(@CurrentUser UserDetailsImpl userDetails) {
        return ResponseEntity.ok(statisticService.getAllScoresOfUsers(userDetails));

    }

    @GetMapping("/data")
    @Transactional

    public ResponseEntity<String> getData(@CurrentUser UserDetailsImpl userDetails) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scores", statisticService.getAllScoresOfUsers(userDetails));
        jsonObject.put("avgAll", moreInfoAboutStatisticRepository.averageOfAllDriving(userDetails.getId()));
        jsonObject.put("allDrivers", userVsAdminRepository.countUsersByAdministratorId(userDetails.getId()));
        jsonObject.put("allTrip", travelRepository.countTripsByAdminId(userDetails.getId()));
        jsonObject.put("allVehicle", vehicleRepository.countByCarAdministratorIdAndIsDeletedIsFalse(userDetails.getId()));
        return ResponseEntity.ok(jsonObject.toString());
    }
}
