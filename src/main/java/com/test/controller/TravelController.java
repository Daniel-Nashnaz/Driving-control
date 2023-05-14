package com.test.controller;

import com.test.dto.AllStatisticsDTO;
import com.test.dto.RealTimeInformationDto;
import com.test.dto.TripsOfUserDto;
import com.test.dto.UsersOfAdminDto;
import com.test.entity.TripStatistic;
import com.test.repository.TripStatisticRepository;
import com.test.security.jwtService.CurrentUser;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.implementation.TravelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {


    private final TravelServiceImpl travelService;
    private final TripStatisticRepository tripStatisticRepository;

    //first view
    @GetMapping("/getAllUsersByLastTravel")
    public ResponseEntity<List<UsersOfAdminDto>> getAllLastTravelOfUsersByAdmin(@CurrentUser UserDetailsImpl userDetails) {
        List<UsersOfAdminDto> allUsersByAdmin = travelService.getAllLastTravelOfUsersByAdminId(userDetails);
        return ResponseEntity.ok(allUsersByAdmin);
    }
    //after click on user... show this
    @GetMapping("/getLastTripsByUser/{id}")
    public ResponseEntity<List<TripsOfUserDto>> getLastTripsByUser(@PathVariable Integer id) {
        List<TripsOfUserDto> lastTripsByUser = travelService.getLastTripsByUserId(id);
        return ResponseEntity.ok(lastTripsByUser);
    }
    //after click on trip statistic show this
    @GetMapping("getStatisticsOfTripId/{tripId}")
    public ResponseEntity<Optional<AllStatisticsDTO>> getStatisticsOfTripById(@PathVariable Integer tripId) {
        return ResponseEntity.ok().body(travelService.getStatisticsOfTripId(tripId));
    }
    //after click on trip info show this
   @GetMapping("getAllDataAboutTripId/{tripId}")
   public ResponseEntity<List<RealTimeInformationDto>> getAllDataOfTripById(@PathVariable Integer tripId) {
       List<RealTimeInformationDto> allInformation = travelService.getAllDataOfTripId(tripId);
       return ResponseEntity.ok().body(allInformation);
   }


}
