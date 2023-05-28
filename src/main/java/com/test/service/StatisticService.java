package com.test.service;

import com.test.dto.UserTripAverageDto;
import com.test.security.jwtService.UserDetailsImpl;

import java.util.List;

public interface StatisticService {


    List<UserTripAverageDto> getAllScoresOfUsers(UserDetailsImpl userDetails);
}
