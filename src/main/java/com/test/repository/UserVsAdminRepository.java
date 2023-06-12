package com.test.repository;

import com.test.entity.UserVsAdmin;
import com.test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserVsAdminRepository extends JpaRepository<UserVsAdmin, Integer> {
    @Query("SELECT uva FROM UserVsAdmin uva join fetch uva.userID u " +
            "join fetch u.userVsRoles r join fetch r.roleID " +
            " where uva.administratorID.id = :administratorID and u.isDeleted=false")
    List<UserVsAdmin> findUsersByAdministratorId(@Param("administratorID") Integer administratorID);

    @Query("SELECT COUNT(*) " +
            "FROM Users u INNER JOIN UserVsAdmin uva " +
            "ON u.id = uva.userID.id " +
            "where uva.administratorID.id = :administratorID and u.isDeleted=false")
    Integer countUsersByAdministratorId(@Param("administratorID") Integer administratorID);


    @Query("SELECT uva.administratorID.id FROM UserVsAdmin uva " +
            "JOIN Users u ON uva.userID.id = u.id " +
            "WHERE uva.userID.id = :userId")
    Integer getAdministratorIdForUser(@Param("userId") int userId);





}