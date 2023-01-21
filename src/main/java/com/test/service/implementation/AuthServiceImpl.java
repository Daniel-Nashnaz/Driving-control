package com.test.service.implementation;

import com.test.dto.LoginDto;
import com.test.dto.RegistrationDto;
import com.test.dto.UpdateUser;
import com.test.entity.*;
import com.test.exception.AuthApiException;
import com.test.repository.*;
//import com.test.security.JwtTokenProvider;
import com.test.security.jwt.JwtUtils;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    //private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    private final UserPasswordRepository passwordRepository;


    private final UserVsRoleRepository userVsRoleRepository;


    private final RoleRepository roleRepository;
    private final UserVsAdminRepository userVsAdminRepository;


    @Override
    public String registerAdmin(RegistrationDto registrationDto) {
        register(registrationDto);
        return "Admin registered successfully!.";
    }

    @Override
    public String login(LoginDto loginDto) {

        return "sd";
    }

    @Override
    public String adminAddUser(RegistrationDto registrationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserVsAdmin userVsAdmin = new UserVsAdmin();

        userVsAdmin.setUserID(register(registrationDto));

        userVsAdmin.setAdministratorID(userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get());
        // try to do
        // userVsAdmin.setAdministratorID(((Users) auth.getPrincipal()));

        userVsAdminRepository.save(userVsAdmin);

        return "User add successfully!";
    }

    @Override
    public ResponseCookie updateUser(UpdateUser updateUser) {
        isNotExsist(updateUser.getUserName(), updateUser.getEmail());
        ResponseCookie jwtCookie = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userUpdate = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get();
        if (updateUser.getFullName() != null) {
            userUpdate.setFullName(updateUser.getFullName());
            ((UserDetailsImpl) auth.getPrincipal()).setFullName(updateUser.getFullName());
        }

        if (updateUser.getEmail() != null && updateUser.getUserName() != null && updateUser.getPassword() != null) {
            userUpdate.setEmail(updateUser.getEmail());
            ((UserDetailsImpl) auth.getPrincipal()).setEmail(updateUser.getEmail());
            userUpdate.setUserName(updateUser.getUserName());
            ((UserDetailsImpl) auth.getPrincipal()).setUsername(updateUser.getUserName());
            String pass = passwordEncoder.encode(updateUser.getPassword());
            userUpdate.getUserPasswords().stream().findFirst().get().setPassword(pass);
            ((UserDetailsImpl) auth.getPrincipal()).setPassword(pass);
            jwtCookie = jwtUtils.generateJwtCookie(((UserDetailsImpl) auth.getPrincipal()));
            return jwtCookie;
        }
        if (updateUser.getEmail() != null) {
            userUpdate.setEmail(updateUser.getEmail());
            ((UserDetailsImpl) auth.getPrincipal()).setEmail(updateUser.getEmail());
            jwtCookie = jwtUtils.generateJwtCookie(((UserDetailsImpl) auth.getPrincipal()));
        }
        if (updateUser.getUserName() != null) {
            userUpdate.setUserName(updateUser.getUserName());
            ((UserDetailsImpl) auth.getPrincipal()).setUsername(updateUser.getUserName());
            jwtCookie = jwtUtils.generateJwtCookie(((UserDetailsImpl) auth.getPrincipal()));

        }
        if (updateUser.getPassword() != null) {
            String pass = passwordEncoder.encode(updateUser.getPassword());
            userUpdate.getUserPasswords().stream().findFirst().get().setPassword(pass);
            ((UserDetailsImpl) auth.getPrincipal()).setPassword(pass);
            jwtCookie = jwtUtils.generateJwtCookie(((UserDetailsImpl) auth.getPrincipal()));
        }

        userRepository.save(userUpdate);
        return jwtCookie;
    }

    @Override
    public String deleteCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> userDelete = userRepository.findById(((UserDetailsImpl)auth.getPrincipal()).getId());
        userDelete.get().setIsDeleted(true);
        userDelete.get().getUserPasswords()
                .stream()
                .filter(userPassword -> !userPassword.getIsActive())
                .findFirst()
                .ifPresent(userPassword -> userPassword.setIsActive(true));

    userRepository.save(userDelete.get());
    return "User delete successfully!";
    }

    private void isNotExsist(String userName, String email) {
        if (userRepository.existsByUsername(userName)) {
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }
    }

    private Users register(RegistrationDto registrationDto) {
        isNotExsist(registrationDto.getUserName(), registrationDto.getEmail());
        Users user = new Users(registrationDto.getFullName(), registrationDto.getUserName(), registrationDto.getEmail());

        UserPassword userPassword = new UserPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userPassword.setUserID(user);
        user.setUserPasswords(Collections.singleton(userPassword));

        Role userRole = roleRepository.findByRuleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AuthApiException(HttpStatus.NOT_IMPLEMENTED, "User Role not set."));
        UserVsRole userVsRole = new UserVsRole();
        userVsRole.setRoleID(userRole);
        userVsRole.setUserID(user);
        user.setUserVsRoles(Collections.singleton(userVsRole));
        userRepository.save(user);
        passwordRepository.save(userPassword);
        userVsRoleRepository.save(userVsRole);

        return user;
    }


}
