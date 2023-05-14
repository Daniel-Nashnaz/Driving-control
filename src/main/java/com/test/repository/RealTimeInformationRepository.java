package com.test.repository;

import com.test.entity.RealTimeInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealTimeInformationRepository extends JpaRepository<RealTimeInformation, Long> {

    List<RealTimeInformation> getAllByTripID_Id(Integer tripID);
}