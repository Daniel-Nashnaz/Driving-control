package com.test.controller;

import com.test.dto.*;
import com.test.entity.RefreshToken;
import com.test.entity.Users;
import com.test.repository.UserRepository;
import com.test.service.AuthService;
import com.test.testttt.ser.JwtUtils;
import com.test.testttt.x.RefreshTokenService;
import com.test.testttt.x.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/v1/auth/"})
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    private final AuthService authService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;




    @GetMapping("/v1")
    public Optional<Users> userList() {
        return userRepository.findById(3);
    }

    @GetMapping("/test1")
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
//        String token = authService.login(loginDto);
//
//        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
//        jwtAuthResponse.setAccessToken(token);
//
//
//        return ResponseEntity.ok(jwtAuthResponse);
        //return ResponseEntity.ok("finish");

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),userDetails.getUsername(), userDetails.getEmail(), roles));
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
