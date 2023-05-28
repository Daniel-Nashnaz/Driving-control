package com.test.controller;

import com.test.dto.UserTripAverageDto;
import com.test.mapper.StatisticMapper;
import com.test.repository.TravelRepository;
import com.test.security.jwtService.CurrentUser;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.implementation.StatisticServiceImpl;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/allScore")
    @Transactional
    public List<UserTripAverageDto> allScores(@CurrentUser UserDetailsImpl userDetails) {
        return statisticService.getAllScoresOfUsers(userDetails);

    }
}
