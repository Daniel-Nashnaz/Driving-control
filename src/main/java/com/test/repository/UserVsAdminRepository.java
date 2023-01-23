package com.test.repository;

import com.test.entity.UserVsAdmin;
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


}