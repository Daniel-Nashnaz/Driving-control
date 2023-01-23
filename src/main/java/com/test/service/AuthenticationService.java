package com.test.service;

import com.test.dto.ApiResponse;
import com.test.dto.LoginDto;
import com.test.dto.RegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    String registerAdmin(RegistrationDto registrationDto);

    ResponseEntity<?> login(LoginDto loginDto);

    ResponseEntity<ApiResponse> logout(String user);

    ResponseEntity<?> refreshToken(HttpServletRequest request);
}
