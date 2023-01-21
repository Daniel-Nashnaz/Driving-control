package com.test.security.jwtService;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.test.entity.RefreshToken;
import com.test.exception.TokenRefreshException;
import com.test.repository.RefreshTokenRepository;
import com.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;


    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;


    //@Transactional(propagation = Propagation.REQUIRED)

    public Optional<RefreshToken> findByToken(String token) {
        Optional<RefreshToken> byToken = refreshTokenRepository.findByToken(token);

//        Hibernate.initialize(byToken.get().getUserID());
//        Hibernate.initialize(byToken.get().getUserID().getUserVsAdmins());
//        Hibernate.initialize(byToken.get().getUserID().getUserPasswords());
//        Hibernate.initialize(byToken.get().getUserID().getUserVsRoles());
//        Hibernate.initialize(byToken.get().getUserID().getAdminsVsUser());

        return byToken;
    }


    public RefreshToken createRefreshToken(int userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserID(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public Integer deleteByUserId(Integer userId) {
        return refreshTokenRepository.deleteByUserID(userRepository.findById(userId).get());
    }


}
