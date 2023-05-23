package com.test.mapper;


import com.test.dto.RealTimeInformationDto;
import com.test.entity.RealTimeInformation;

public class TravelAndTripMapper {

    public static RealTimeInformationDto RealTimeInformationToDto(RealTimeInformation realTimeInformation) {
        return new RealTimeInformationDto()
                .setTimeFromStart(realTimeInformation.getTimeFromStart())
                .setLatitude(realTimeInformation.getLatitude())
                .setLongitude(realTimeInformation.getLongitude())
                .setForwardWarningDirections(realTimeInformation.getForwardWarningDirections())
                .setForwardWarningDistance(realTimeInformation.getForwardWarningDistance())
                .setTimeFromStart(realTimeInformation.getTimeFromStart())
                .setLaneDepartureWarning(realTimeInformation.getLaneDepartureWarning())
                .setPedestrianAndCyclistCollisionWarning(realTimeInformation.getPedestrianAndCyclistCollisionWarning())
                .setSuddenBraking(realTimeInformation.getSuddenBraking())
                .setSpeedAllowed(realTimeInformation.getSpeedAllowed())
                .setCurrentSpeed(realTimeInformation.getCurrentSpeed())
                .setDistanceTraveledMile(realTimeInformation.getDistanceTraveledMile());


    }
}
