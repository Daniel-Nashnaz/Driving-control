package com.test.controller;

import com.test.dto.*;
import com.test.entity.RefreshToken;
import com.test.entity.Users;
import com.test.exception.TokenRefreshException;
import com.test.repository.UserRepository;
import com.test.service.AuthService;
import com.test.security.jwt.JwtUtils;
import com.test.security.jwtService.RefreshTokenService;
import com.test.security.jwtService.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<UserInfoResponse> login(@Valid @RequestBody LoginDto loginDto) {
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
                .body(new UserInfoResponse(userDetails.getId(), userDetails.getFullName(), userDetails.getUsername(), userDetails.getEmail(), roles));
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


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            Integer userId = ((UserDetailsImpl) principle).getId();
            refreshTokenService.deleteByUserId(userId);
        }

        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new ApiResponse(Instant.now(),"You've been signed out!",null));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            //to do fetch = FetchType.LAZY....
            Optional<RefreshToken> byToken = refreshTokenService.findByToken(refreshToken);
                  return byToken.map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUserID)
                    .map(user -> {
                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new ApiResponse(Instant.now(), "Token is refreshed successfully!", null));
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,"Refresh token is not in database!"));
        }

        return new ResponseEntity<>(new ApiResponse(Instant.now(), "Refresh Token is empty!", null), HttpStatus.CREATED);
    }

}
