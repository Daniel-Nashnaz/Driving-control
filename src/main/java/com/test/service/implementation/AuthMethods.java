package com.test.service.implementation;

import com.test.dto.RegistrationDto;
import com.test.dto.UserInfoResponse;
import com.test.entity.*;
import com.test.exception.AuthApiException;
import com.test.repository.RoleRepository;
import com.test.repository.UserPasswordRepository;
import com.test.repository.UserRepository;
import com.test.repository.UserVsRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthMethods {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordRepository passwordRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserVsRoleRepository userVsRoleRepository;

    @Autowired
    private RoleRepository roleRepository;


    public void setAllPassNotActive(Users userUpdate) {
        userUpdate.getUserPasswords()
                .stream()
                .filter(up -> up.getIsActive())
                .map(userPassword -> {
                    if (userPassword.getIsActive())
                        userPassword.setIsActive(false);
                    return userPassword;
                }).collect(Collectors.toSet());
        userRepository.save(userUpdate);
    }


    public void getAllPassAndCheckIfUsedBefore(Users userUpdate, String newPass) {
        List<UserPassword> allByUserID = passwordRepository.findAllByUserID(userUpdate);
        allByUserID.stream().forEach(userPassword -> {
            boolean match = passwordEncoder.matches(newPass, userPassword.getPassword());
            if (match) {
                throw new AuthApiException(HttpStatus.BAD_REQUEST, "Please choose a password that you haven't used before.");
            }
        });
    }

    public Users register(RegistrationDto registrationDto, RoleName roleName) {
        isNotExsist(registrationDto.getUserName(), registrationDto.getEmail());
        Users user = new Users(registrationDto.getFullName(), registrationDto.getUserName(), registrationDto.getEmail(),registrationDto.getPhone());
        Address address = new Address(registrationDto.getAddress(),registrationDto.getApartmentNumber(),registrationDto.getCity(),registrationDto.getCountry());
        UserPassword userPassword = new UserPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userPassword.setIsActive(true);
        userPassword.setUserID(user);
        address.setUserID(user);
        user.setUserPasswords(Collections.singleton(userPassword));
        user.setAddresses(Collections.singletonList(address));
        Role userRole = roleRepository.findByRuleName(roleName)
                .orElseThrow(() -> new AuthApiException(HttpStatus.NOT_IMPLEMENTED, "User Role not set."));
        UserVsRole userVsRole = new UserVsRole();
        userVsRole.setRoleID(userRole);
        userVsRole.setUserID(user);
        user.setUserVsRoles(Collections.singleton(userVsRole));
        userRepository.save(user);
        // passwordRepository.save(userPassword);
        userVsRoleRepository.save(userVsRole);
        return user;
    }

    public void isNotExsist(String userName, String email) {
        if (userRepository.existsByUsername(userName)) {
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }
    }
}
