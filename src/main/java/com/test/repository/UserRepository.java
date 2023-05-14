package com.test.repository;

import com.test.dto.UsersOfAdminDto;
import com.test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("select (count(u) > 0) from Users u where u.userName = ?1")
    boolean existsByUsername(String userName);

    @Query("select (count(u) > 0) from Users u where upper(u.email) = upper(:email)")
    boolean existsByEmail(String email);
      //  @Query("SELECT new Users(e.email, e.fullName, e.userName) FROM Users e")
   // List<Users> findByQuery();
        @Procedure("SearchEmployee")
        List<Users> searchEmployee(String userName);

    @Query("SELECT new com.test.dto.UsersOfAdminDto(u.id, t.id, u.fullName, u.userName, u.email, u.phone) " +
            "FROM Users u " +
            "INNER JOIN UserVsAdmin ua ON u.id = ua.userID.id " +
            "INNER JOIN Travel t ON u.id = t.userID.id " +
            "WHERE ua.administratorID.id = ?1 " +
            "AND t.travelStart = (SELECT MAX(t2.travelStart) FROM Travel t2 WHERE t2.userID.id = u.id) " +
            "AND u.isDeleted = false " +
            "ORDER BY t.travelStart ASC")
    List<UsersOfAdminDto> findTop100UsersOfAdmin(Integer adminId);


    Optional<Users> findByUserNameAndIsDeletedFalseOrEmailAndIsDeletedFalse(String userName, String email);

}