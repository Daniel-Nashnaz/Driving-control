package com.test.service.implementation;

import com.test.dto.*;
import com.test.entity.*;
import com.test.repository.*;
import com.test.security.jwt.JwtUtils;
import com.test.security.jwtService.RefreshTokenService;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.ActionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionsServiceImpl implements ActionsService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserVsAdminRepository userVsAdminRepository;
    private final AuthMethods authMethods;



    @Override
    public String adminAddUser(RegistrationDto registrationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserVsAdmin userVsAdmin = new UserVsAdmin();

        userVsAdmin.setUserID(authMethods.register(registrationDto, RoleName.ROLE_USER));

        userVsAdmin.setAdministratorID(userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get());
        // try to do
        // userVsAdmin.setAdministratorID(((Users) auth.getPrincipal()));

        userVsAdminRepository.save(userVsAdmin);

        return "User add successfully!";
    }

    @Override
    public ResponseCookie updateUser(UpdateUser updateUser) {
        authMethods.isNotExsist(updateUser.getUserName(), updateUser.getEmail());
        ResponseCookie jwtCookie = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userUpdate = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get();
        if (updateUser.getFullName() != null) {
            userUpdate.setFullName(updateUser.getFullName());
            ((UserDetailsImpl) auth.getPrincipal()).setFullName(updateUser.getFullName());
        }

        if (updateUser.getPhone() != null) {
            userUpdate.setPhone(updateUser.getPhone());
            ((UserDetailsImpl) auth.getPrincipal()).setEmail(updateUser.getEmail());
        }

        if (updateUser.getEmail() != null && updateUser.getUserName() != null && updateUser.getPassword() != null) {
            authMethods.getAllPassAndCheckIfUsedBefore(userUpdate, updateUser.getPassword());
            userUpdate.setEmail(updateUser.getEmail());
            ((UserDetailsImpl) auth.getPrincipal()).setEmail(updateUser.getEmail());
            userUpdate.setUserName(updateUser.getUserName());
            ((UserDetailsImpl) auth.getPrincipal()).setUsername(updateUser.getUserName());
            String pass = passwordEncoder.encode(updateUser.getPassword());
            authMethods.setAllPassNotActive(userUpdate);
            UserPassword userPassword = new UserPassword();
            userPassword.setPassword(pass);
            userPassword.setIsActive(true);
            userPassword.setUserID(userUpdate);
            userUpdate.setUserPasswords(Collections.singleton(userPassword));
            ((UserDetailsImpl) auth.getPrincipal()).setPassword(pass);
            userRepository.save(userUpdate);
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
            authMethods.getAllPassAndCheckIfUsedBefore(userUpdate, updateUser.getPassword());
            String pass = passwordEncoder.encode(updateUser.getPassword());
            authMethods.setAllPassNotActive(userUpdate);
            UserPassword userPassword = new UserPassword();
            userPassword.setPassword(pass);
            userPassword.setIsActive(true);
            userPassword.setUserID(userUpdate);
            userUpdate.setUserPasswords(Collections.singleton(userPassword));
            ((UserDetailsImpl) auth.getPrincipal()).setPassword(pass);
            jwtCookie = jwtUtils.generateJwtCookie(((UserDetailsImpl) auth.getPrincipal()));
        }

        userRepository.save(userUpdate);
        return jwtCookie;
    }


    @Override
    public void deleteById(Integer id) {
        Optional<Users> userDelete = userRepository.findById(id);
        userDelete.get().setIsDeleted(true);
        userDelete.get().getUserPasswords()
                .stream()
                .filter(userPassword -> userPassword.getIsActive())
                .findFirst()
                .ifPresent(userPassword -> userPassword.setIsActive(false));
        userRepository.save(userDelete.get());
    }

    @Override
    public List<UserInfoResponse> getAllUserOfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<UserVsAdmin> usersByAdministratorId = userVsAdminRepository.findUsersByAdministratorId((((UserDetailsImpl) auth.getPrincipal()).getId()));
        return usersByAdministratorId.stream().map(user -> authMethods.mapperUserVsAdminToDTO(user)).collect(Collectors.toList());
    }


    @Override
    public String deleteCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        deleteById((((UserDetailsImpl) auth.getPrincipal()).getId()));
        return "User delete successfully!";
    }



}
