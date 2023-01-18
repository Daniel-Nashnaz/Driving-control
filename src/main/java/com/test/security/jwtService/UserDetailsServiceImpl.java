package com.test.security.jwtService;


import com.test.entity.Users;
import com.test.exception.AuthApiException;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Users user = userRepository.findByUserNameOrEmail(username,username)
                //.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
                .orElseThrow(() -> new AuthApiException(HttpStatus.UNAUTHORIZED, "User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
