package com.test.controller;

import com.test.dto.ApiResponse;
import com.test.dto.LoginDto;
import com.test.dto.RegistrationDto;
import com.test.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping({"/api/v1/auth/"})
//@CrossOrigin(origins = "http://localhost:3000"/*, maxAge = 3600*/)
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }


    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<?> registerOfAdmin(@Valid @RequestBody RegistrationDto registerDto, WebRequest webRequest) {
        String response = authenticationService.registerAdmin(registerDto);

        return new ResponseEntity<>(new ApiResponse(Instant.now(), response, webRequest.getDescription(true)), HttpStatus.CREATED);

    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse> logoutUser() {
        String user = "You've been signed out!";
        return authenticationService.logout(user);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
      return authenticationService.refreshToken(request);
    }

}
