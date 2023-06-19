package com.test.service;

import com.test.dto.EcosystemTripDto;
import com.test.dto.EcosystemUserCountDTO;

import java.util.List;

public interface EcosystemService {

    List<EcosystemUserCountDTO> getAllEcologyByUser(Integer userId);

    List<EcosystemTripDto> getAllEcologyByTrip(Integer tripId);

    List<EcosystemUserCountDTO> getAllEcologyOfDriversByAdmin(Integer adminId);
}
