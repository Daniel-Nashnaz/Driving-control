package com.test.repository;

import com.test.entity.UserPassword;
import com.test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {

   // Optional<UserPassword> findByUserId(Integer userID);
    UserPassword findByUserIDAndIsActiveFalse(Users user);
}