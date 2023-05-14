package com.test.controller;

import com.test.dto.*;
import com.test.repository.MessageRepository;
import com.test.repository.UsersAllowSendingMessageRepository;
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
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class Actions {

    private final ActionsService actionsService;

    private final AuthenticationService authenticationService;
    private final UsersAllowSendingMessageRepository usersAllowSendingMessageRepository;
    private final MessageRepository messageRepository;

    @PreAuthorize("permitAll")
    @GetMapping("/all")
    public String allAccess() {
        return "i am all";
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
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody RegistrationDto registerDto) {
        //to do method of send email of user with my details and change pass
        return ResponseEntity.ok()
                .body(new ApiResponse(Instant.now(), actionsService.adminAddUser(registerDto), null));


    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> adminUpdateUser(@PathVariable Integer id,@Valid @RequestBody UpdateUser updateUser) {
        System.out.println(updateUser);
        String resMes = actionsService.updateUserById(id, updateUser);
        return ResponseEntity.ok()
                .body(new ApiResponse(Instant.now(), resMes, null));

    }
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    //@PostAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUser updateUser) {
        ResponseCookie cookie = actionsService.updateUser(updateUser);
        if (cookie == null) {
            return ResponseEntity.ok()
                    .body(new ApiResponse(Instant.now(), "User update successfully!", null));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse(Instant.now(), "User update successfully!", null));

    }

    //curl -X DELETE http://localhost:8080/home/delete/1
    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Integer id, WebRequest webRequest) {
        actionsService.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse(Instant.now(), "User deleted successfully! ", webRequest.getDescription(true)));

    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCurrentUser(WebRequest webRequest) {
        String user = actionsService.deleteCurrentUser();
        return authenticationService.logout(user);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/api/v1/auth/signout")
//                .buildAndExpand().toUri();
//        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserInfoResponse>> getAllUserOfAdmin() {
        List<UserInfoResponse> allUser = actionsService.getAllUserOfAdmin();
        return ResponseEntity.ok().body(allUser);
    }


    @GetMapping("/user-details")
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AddressDto>> getAddressOfUser(@CurrentUser UserDetailsImpl currentUser) {
        return ResponseEntity.ok(actionsService.getAddressOfCurrentUser(currentUser));
    }
    @GetMapping("/addressById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AddressDto>> getAddressOfUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(actionsService.getAddressOfUserId(id));
    }


    @GetMapping("/getAllMessagesSendOfCurrentUser")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDto>> getAllMessagesSendOfCurrentUser(@CurrentUser UserDetailsImpl currentUser) {
        return ResponseEntity.ok().body(actionsService.getAllMessagesSendOfCurrentUser(currentUser));

    }

    @GetMapping("/getAllMessagesSendOfUserId/{id}")
    @PostAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MessageDto>> getAllMessagesSendOftUserId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(actionsService.getAllMessagesSendOftUserId(id));

    }

    @PostMapping("/addAdminIfAllowMessage")
    @PostAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addAdminIfAllowMessage(@Valid @RequestBody UsersAllowSendingMessageDto usersAllowSendingMessageDto) {
        return ResponseEntity.ok().body(new ApiResponse(Instant.now(),actionsService.addAdminIfWantToGetMessages(usersAllowSendingMessageDto),null));

    }

    @GetMapping("/getAllMessagesSendById/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersAllowSendingMessageDto>> getAllMessagesSendById(@PathVariable Integer id) {
        return ResponseEntity.ok(actionsService.getMessageSendSettingsOfUserId(id));

    }



}
