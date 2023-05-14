package com.test.service;

import com.test.dto.AllStatisticsDTO;
import com.test.dto.RealTimeInformationDto;
import com.test.dto.TripsOfUserDto;
import com.test.dto.UsersOfAdminDto;
import com.test.security.jwtService.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TravelService {

    List<UsersOfAdminDto> getAllLastTravelOfUsersByAdminId(UserDetailsImpl currentUser);

    List<TripsOfUserDto> getLastTripsByUserId(Integer id);

    List<RealTimeInformationDto> getAllDataOfTripId(Integer tripId);

    Optional<AllStatisticsDTO> getStatisticsOfTripId(Integer tripId);
}
