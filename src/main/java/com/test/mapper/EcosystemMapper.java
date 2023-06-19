package com.test.mapper;

import com.test.dto.EcosystemTripDto;
import com.test.dto.EcosystemUserCountDTO;

import java.util.ArrayList;
import java.util.List;

public class EcosystemMapper {

    public static List<EcosystemUserCountDTO> mapToEcosystemCountDto(List<Object[]> rows) {
        ArrayList<EcosystemUserCountDTO> ecosystems = new ArrayList<>();
        for (Object[] row : rows) {
            EcosystemUserCountDTO ecosystemUser = new EcosystemUserCountDTO()
                    .setEcologicalType((String) row[0])
                    .setCount((int) row[1]);
            ecosystems.add(ecosystemUser);
        }
        return ecosystems;

    }


    public static List<EcosystemTripDto> mapToEcosystemTripDto(List<Object[]> rows) {
        ArrayList<EcosystemTripDto> ecosystems = new ArrayList<>();
        for (Object[] row : rows) {
            EcosystemTripDto ecosystemTrip = new EcosystemTripDto()
                    .setLatitude((double) row[0])
                    .setLongitude((double) row[1])
                    .setEcologicalType((String) row[2])
                    .setCurrentSpeed((int) row[3])
                    .setDistanceTraveledMile((double) row[4]);
            ecosystems.add(ecosystemTrip);
        }
        return ecosystems;

    }


    public static List<EcosystemUserCountDTO> mapToEcosystemCountOfAdminDto(List<Object[]> rows) {
        ArrayList<EcosystemUserCountDTO> ecosystemsOfAdmin = new ArrayList<>();
        for (Object[] row : rows) {
            EcosystemUserCountDTO ecosystemUser = new EcosystemUserCountDTO()
                    .setEcologicalType((String) row[0])
                    .setCount((int) row[1])
                    .setFullName((String) row[2])
                    .setUserId((int) row[3]);

            ecosystemsOfAdmin.add(ecosystemUser);
        }
        return ecosystemsOfAdmin;

    }
}
