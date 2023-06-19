package com.test.repository;

import com.test.entity.Ecosystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EcosystemRepository extends JpaRepository<Ecosystem, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM GetAllEcologyByUserId(?)")
    List<Object[]> getAllEcologyByUserId(int userId);


    @Query(nativeQuery = true, value = "SELECT * FROM GetAllEcologyByTripId(?)")
    List<Object[]> getAllEcologyByTripId(int tripId);


    @Query(nativeQuery = true, value = "SELECT * FROM getAllEcologyOfDriversByAdminId(?)")
    List<Object[]> getAllEcologyOfDriversByAdminId(int adminId);



}