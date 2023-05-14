package com.test.service.implementation;

import com.test.dto.AllStatisticsDTO;
import com.test.dto.RealTimeInformationDto;
import com.test.dto.TripsOfUserDto;
import com.test.dto.UsersOfAdminDto;
import com.test.entity.RealTimeInformation;
import com.test.mapper.TravelAndTripMapper;
import com.test.repository.RealTimeInformationRepository;
import com.test.repository.TravelRepository;
import com.test.repository.TripStatisticRepository;
import com.test.repository.UserRepository;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {


    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final RealTimeInformationRepository realTimeInformationRepository;
    private final TripStatisticRepository tripStatisticRepository;


    @Override
    public List<UsersOfAdminDto> getAllLastTravelOfUsersByAdminId(UserDetailsImpl currentUser) {
        return userRepository.findTop100UsersOfAdmin(currentUser.getId());
    }


    @Override
    public List<TripsOfUserDto> getLastTripsByUserId(Integer id) {
        return travelRepository.findLatestTripsByUser(id);
    }

    @Override
    public Optional<AllStatisticsDTO> getStatisticsOfTripId(Integer tripId) {
        return tripStatisticRepository.findAllWithMoreInfo(tripId);
    }

    @Override
    public List<RealTimeInformationDto> getAllDataOfTripId(Integer tripId) {
        List<RealTimeInformation> allByTripID = realTimeInformationRepository.getAllByTripID_Id(tripId);
        return allByTripID.stream().map(realTimeInformation -> TravelAndTripMapper.RealTimeInformationToDto(realTimeInformation)).collect(Collectors.toList());
    }
}
