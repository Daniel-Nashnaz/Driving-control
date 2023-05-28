package com.test.service.implementation;

import com.test.dto.UserTripAverageDto;
import com.test.mapper.StatisticMapper;
import com.test.repository.TravelRepository;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final TravelRepository travelRepository;
    @Override
    public List<UserTripAverageDto> getAllScoresOfUsers(UserDetailsImpl userDetails) {


        List<Object[]> userTripAverages = travelRepository.getUserTripAverages(userDetails.getId());
        return StatisticMapper.mapToUserTripAverages(userTripAverages);
    }
}
