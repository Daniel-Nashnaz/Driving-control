package com.test.service.implementation;

import com.test.dto.EcosystemTripDto;
import com.test.dto.EcosystemUserCountDTO;
import com.test.repository.EcosystemRepository;
import com.test.service.EcosystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.test.mapper.EcosystemMapper.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EcosystemServiceImpl implements EcosystemService {

    private final EcosystemRepository ecosystemRepository;

    @Override
    public List<EcosystemUserCountDTO> getAllEcologyByUser(Integer userId) {
        return mapToEcosystemCountDto(ecosystemRepository.getAllEcologyByUserId(userId));
    }

    @Override
    public List<EcosystemTripDto> getAllEcologyByTrip(Integer tripId) {
        return mapToEcosystemTripDto(ecosystemRepository.getAllEcologyByTripId(tripId));
    }

    @Override
    public List<EcosystemUserCountDTO> getAllEcologyOfDriversByAdmin(Integer adminId) {
        return mapToEcosystemCountOfAdminDto(ecosystemRepository.getAllEcologyOfDriversByAdminId(adminId));
    }
}
