package com.test.service.implementation;

import com.test.dto.LoginDto;
import com.test.dto.RegistrationDto;
import com.test.entity.*;
import com.test.exception.AuthApiException;
import com.test.repository.RoleRepository;
import com.test.repository.UserPasswordRepository;
import com.test.repository.UserRepository;
import com.test.repository.UserVsRoleRepository;
//import com.test.security.JwtTokenProvider;
import com.test.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    //@Autowired
    //private AuthenticationManager authenticationManager;
    //@Autowired
    //private JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired

    private UserPasswordRepository passwordRepository;
    @Autowired

    private UserVsRoleRepository userVsRoleRepository;
    @Autowired

    private RoleRepository roleRepository;


    @Override
    public String registerAdmin(RegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUserName())) {
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        //TODO mapper.
        Users user = new Users(registrationDto.getFullName(), registrationDto.getUserName(), registrationDto.getEmail());

        UserPassword userPassword = new UserPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userPassword.setUserID(user);
        user.setUserPasswords(Collections.singleton(userPassword));

        Role userRole = roleRepository.findByRuleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AuthApiException(HttpStatus.NOT_IMPLEMENTED, "User Role not set."));
        UserVsRole userVsRole = new UserVsRole();
        userVsRole.setRoleID(userRole);
        userVsRole.setUserID(user);
        user.setUserVsRoles(Collections.singleton(userVsRole));
        userRepository.save(user);
        passwordRepository.save(userPassword);
        userVsRoleRepository.save(userVsRole);

        return "User registered successfully!.";
    }

    @Override
    public String login(LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUserNameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtTokenProvider.generateToken(authentication);
//
//        return token;
        return "sd";
    }

}
