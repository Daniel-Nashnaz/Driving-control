package com.test.controller;

import com.test.dto.*;
import com.test.security.jwtService.CurrentUser;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.ActionsService;
import com.test.service.AuthenticationService;
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

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class Actions {

    //TODO add env to passwords!!!
    private final ActionsService actionsService;

    private final AuthenticationService authenticationService;


    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody RegistrationDto registerDto) {
        //to do method of send email of user with my details and change pass
        return ResponseEntity.ok()
                .body(new ApiResponse(Instant.now(), actionsService.adminAddUser(registerDto), null));


    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> adminUpdateUser(@PathVariable Integer id,@Valid @RequestBody UpdateUser updateUser) {
        String resMes = actionsService.updateUserById(id, updateUser);
        return ResponseEntity.ok()
                .body(new ApiResponse(Instant.now(), resMes, null));

    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UpdateCurrentUserDto updateCurrentUserDto) {
        ResponseCookie cookie = actionsService.updateUser(updateCurrentUserDto);
        if (cookie == null) {
            return ResponseEntity.ok()
                    .body(new ApiResponse(Instant.now(), "User update successfully!", null));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse(Instant.now(), "User update successfully!", null));

    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ApiResponse> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        String info = actionsService.updatePasswordOfCurrentUser(updatePasswordDto);
        return ResponseEntity.ok()
                .body(new ApiResponse(Instant.now(), info, null));

    }


    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Integer id, WebRequest webRequest) {
        actionsService.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse(Instant.now(), "User deleted successfully! ", webRequest.getDescription(true)));

    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteCurrentUser(WebRequest webRequest) {
        String user = actionsService.deleteCurrentUser();
        return authenticationService.logout(user);

    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserInfoResponse>> getAllUserOfAdmin() {
        List<UserInfoResponse> allUser = actionsService.getAllUserOfAdmin();
        return ResponseEntity.ok().body(allUser);
    }


    @GetMapping("/user-details")
    public ResponseEntity<UserInfoResponse> userDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Integer id = ((UserDetailsImpl) auth.getPrincipal()).getId();
        String fullName = ((UserDetailsImpl) auth.getPrincipal()).getFullName();
        String phone = ((UserDetailsImpl) auth.getPrincipal()).getPhone();
        String email = ((UserDetailsImpl) auth.getPrincipal()).getEmail();
        List<String> roles = auth.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new UserInfoResponse(id, fullName, username, email, phone, roles));
    }

    @GetMapping("/currentUserAddress")
    public ResponseEntity<List<AddressDto>> getAddressOfUser(@CurrentUser UserDetailsImpl currentUser) {
        return ResponseEntity.ok(actionsService.getAddressOfCurrentUser(currentUser));
    }
    @GetMapping("/addressById/{id}")
    public ResponseEntity<List<AddressDto>> getAddressOfUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(actionsService.getAddressOfUserId(id));
    }


    @GetMapping("/getAllMessagesSendOfCurrentUser")
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<MessageDto>> getAllMessagesSendOfCurrentUser(@CurrentUser UserDetailsImpl currentUser) {
        return ResponseEntity.ok().body(actionsService.getAllMessagesSendOfCurrentUser(currentUser));

    }

    @GetMapping("/getAllMessagesSendOfAdminId/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<MessageDto>> getAllMessagesSendOftUserId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(actionsService.getAllMessagesSendOftAdminId(id));

    }


    @GetMapping("/getAllMessagesSendById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UsersAllowSendingMessageDto>> getAllMessagesSendById(@PathVariable Integer id) {
        return ResponseEntity.ok(actionsService.getMessageSendSettingsOfAdminId(id));

    }
    @PostMapping("/addAllowMessage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> addAdminIfAllowMessage(@Valid @RequestBody UsersAllowSendingMessageDto usersAllowSendingMessageDto) {
        return ResponseEntity.ok().body(new ApiResponse(Instant.now(),actionsService.addAdminIfWantToGetMessages(usersAllowSendingMessageDto),null));
    }

    @PutMapping("/updateAllowMessage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> allowGetMessage(@Valid @RequestBody UsersAllowSendingMessageDto usersAllowSendingMessageDto,WebRequest webRequest){
        actionsService.updateAllowMessageOfAdmin(usersAllowSendingMessageDto);
        return ResponseEntity.ok().body(new ApiResponse(Instant.now(), actionsService.updateAllowMessageOfAdmin(usersAllowSendingMessageDto), webRequest.getDescription(true)));

    }




}
