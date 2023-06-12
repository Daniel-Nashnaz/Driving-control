package com.test.controller;

import com.test.dto.UserTripAverageDto;
import com.test.mapper.StatisticMapper;
import com.test.repository.*;
import com.test.security.jwtService.CurrentUser;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.implementation.StatisticServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticServiceImpl statisticService;


    @GetMapping("/allStatisticOfUser/{userId}")
    @Transactional
    public ResponseEntity<String> allScores(@PathVariable Integer userId,@CurrentUser UserDetailsImpl userDetails) {
        return ResponseEntity.ok(statisticService.getAllStatistic(userId));
    }

    // ret all data to main dashboard!
    @GetMapping("/data")
    @Transactional
    public ResponseEntity<String> getData(@CurrentUser UserDetailsImpl userDetails) {

        return ResponseEntity.ok(statisticService.getAllData(userDetails));
    }

    @GetMapping("/allScoreOfUser/{userId}")
    @Transactional
    public ResponseEntity<String> allScores(@PathVariable Integer userId) {
        return ResponseEntity.ok(statisticService.getAllScoresOfUser(userId));
    }
}
