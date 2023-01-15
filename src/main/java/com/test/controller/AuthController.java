package com.test.controller;

import com.test.dto.ApiResponse;
import com.test.dto.JWTAuthResponse;
import com.test.dto.LoginDto;
import com.test.dto.RegistrationDto;
import com.test.entity.Users;
import com.test.repository.UserRepository;
import com.test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping({"/api/v1/auth/"})
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    private final AuthService authService;



    @GetMapping("/v1")
    public Optional<Users> userList() {
        return userRepository.findById(3);
    }

    @GetMapping("/test1")
    public String testquery() {
        return userRepository.findByQuery().get(0).toString();
    }

    /*    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }*/
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);


        return ResponseEntity.ok(jwtAuthResponse);
        //return ResponseEntity.ok("finish");
    }


    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<?> registerOfAdmin(@Valid @RequestBody RegistrationDto registerDto, WebRequest webRequest) {
        String response = authService.registerAdmin(registerDto);
        //        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();
//.created(location)

        return new ResponseEntity<>(new ApiResponse(Instant.now(), response, webRequest.getDescription(true)), HttpStatus.CREATED);

    }

}
