package com.test.controller;

import com.test.dto.ApiResponse;
import com.test.dto.RegistrationDto;
import com.test.dto.UpdateUser;
import com.test.dto.UserInfoResponse;
import com.test.security.jwt.JwtUtils;
import com.test.security.jwtService.RefreshTokenService;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class admin {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @PreAuthorize("permitAll")
    @GetMapping("/all")
    public String allAccess() {
        return "all can.";
    }

    @PreAuthorize("permitAll")
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/a")
    public RedirectView isAuthentic() {
        System.out.println("sssss");
        return new RedirectView("/all");
    }


    @PostMapping("/addUser")
    @PostAuthorize("hasRole('ADMIN')")
    public String addUser(@Valid @RequestBody RegistrationDto registerDto) {
        //to do method of send email of user with my details and change pass
        return authService.adminAddUser(registerDto);

    }

    @PutMapping("/update")
    @PostAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUser updateUser) {
        ResponseCookie cookie = authService.updateUser(updateUser);
        if (cookie == null) {
            return ResponseEntity.ok()
                    .body(new ApiResponse(Instant.now(), "User update successfully!", null));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse(Instant.now(), "User update successfully!", null));

    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCurrentUser(WebRequest webRequest) {
        String user = authService.deleteCurrentUser();
        //move this to authService.
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
                .body(new ApiResponse(Instant.now(), "You've been signed out!", null));
        //return ResponseEntity.ok().body(new ApiResponse(Instant.now(), user,webRequest.getDescription(true)));

    }

    @GetMapping("/user-details")
    public ResponseEntity<UserInfoResponse> userDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = ((UserDetailsImpl) auth.getPrincipal()).getId();
        String fullName = ((UserDetailsImpl) auth.getPrincipal()).getFullName();
        String email = ((UserDetailsImpl) auth.getPrincipal()).getEmail();
        List<String> roles = auth.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new UserInfoResponse(id, fullName, username, email, roles));
    }


}
