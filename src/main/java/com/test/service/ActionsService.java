package com.test.service;

import com.test.dto.*;
import org.springframework.http.ResponseCookie;

import java.util.List;

public interface ActionsService {

    String adminAddUser(RegistrationDto registrationDto);

    ResponseCookie updateUser(UpdateUser updateUser);

    String deleteCurrentUser();

    void deleteById(Integer id);

    List<UserInfoResponse> getAllUserOfAdmin();

}
