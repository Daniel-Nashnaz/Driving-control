package com.test.service;

import com.test.security.jwtService.UserDetailsImpl;

public interface StatisticService {


    String getAllData(UserDetailsImpl userDetails);



    String getAllStatistic(Integer userId);

    String getAllScoresOfUser(Integer userId);
}
