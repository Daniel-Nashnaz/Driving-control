package com.test.service;

import com.test.dto.LoginDto;
import com.test.dto.RegistrationDto;
import org.springframework.stereotype.Service;


public interface AuthService {
    //String login(LoginDto loginDto);

    String registerAdmin(RegistrationDto registrationDto);

    String login(LoginDto loginDto);
}
