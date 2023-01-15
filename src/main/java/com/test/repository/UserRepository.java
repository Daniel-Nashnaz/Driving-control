package com.test.repository;

import com.test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("select (count(u) > 0) from Users u where u.userName = ?1")
    boolean existsByUsername(String userName);

    @Query("select (count(u) > 0) from Users u where upper(u.email) = upper(:email)")
    boolean existsByEmail(String email);

    @Query("SELECT p.email,p.fullName,p.userName FROM Users p WHERE p.id = :i")
    Optional<Users> findById(int i);

    @Query("SELECT new Users(e.email, e.fullName, e.userName) FROM Users e")
    List<Users> findByQuery();


  Optional<Users> findByUserNameOrEmail(String userName, String email);

}