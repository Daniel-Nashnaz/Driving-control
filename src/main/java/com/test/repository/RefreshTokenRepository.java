package com.test.repository;

import com.test.entity.RefreshToken;
import com.test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    // @Query("select new RefreshToken(r.id,r.userID,r.expiryDate,r.token) from RefreshToken r where r.token = :token")
    // Optional<RefreshToken> findByToken(@Param("token") String token);

    @Query("SELECT r FROM RefreshToken r JOIN FETCH r.userID u JOIN FETCH u.userPasswords " +
            "JOIN FETCH u.userVsRoles ur JOIN FETCH ur.roleID rl  JOIN FETCH u.refreshTokens   WHERE r.token = :token")
    Optional<RefreshToken> findByToken(@Param("token")String token);



    @Modifying
    int deleteByUserID(Users userID);
}