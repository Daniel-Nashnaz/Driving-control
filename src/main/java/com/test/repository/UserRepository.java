package com.test.repository;

import com.test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("select (count(u) > 0) from Users u where u.userName = ?1")
    boolean existsByUsername(String userName);

    @Query("select (count(u) > 0) from Users u where upper(u.email) = upper(:email)")
    boolean existsByEmail(String email);
        @Query("SELECT new Users(e.email, e.fullName, e.userName) FROM Users e")
    List<Users> findByQuery();


  Optional<Users> findByUserNameAndIsDeletedFalseOrEmailAndIsDeletedFalse(String userName, String email);

}