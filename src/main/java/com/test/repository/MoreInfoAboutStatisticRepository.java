package com.test.repository;

import com.test.entity.MoreInfoAboutStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MoreInfoAboutStatisticRepository extends JpaRepository<MoreInfoAboutStatistic, Integer> {

    @Query("SELECT AVG(m.tripScore) " +
            "FROM Travel t INNER JOIN UserVsAdmin uva " +
            "ON t.userID.id = uva.userID.id " +
            "INNER JOIN MoreInfoAboutStatistic m " +
            "ON m.tripID.id = t.id " +
            "where uva.administratorID.id = :adminId")
    Double averageOfAllDriving(@Param("adminId") int adminId);


    @Procedure(procedureName = "[dbo].[GetAllScoresOfUserId]")
    @Transactional(readOnly = true )
    List<Object[]> getAllScoresOfUserId(@Param("userId") int userId);
}