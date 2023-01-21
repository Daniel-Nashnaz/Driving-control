package com.test.service;

import com.test.dto.LoginDto;
import com.test.dto.RegistrationDto;
import com.test.dto.UpdateUser;
import org.springframework.http.ResponseCookie;


public interface AuthService {
    //String login(LoginDto loginDto);

    String registerAdmin(RegistrationDto registrationDto);

    String login(LoginDto loginDto);

    String adminAddUser(RegistrationDto registrationDto);

    ResponseCookie updateUser(UpdateUser updateUser);



    String deleteCurrentUser();
}
