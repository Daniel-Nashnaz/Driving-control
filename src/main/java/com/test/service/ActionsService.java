package com.test.service;

import com.test.dto.*;
import com.test.security.jwtService.UserDetailsImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ActionsService {

    String adminAddUser(RegistrationDto registrationDto);

    ResponseCookie updateUser(UpdateUser updateUser);

    String updateUserById(Integer id,UpdateUser updateUser);

    String deleteCurrentUser();

    void deleteById(Integer id);

    List<UserInfoResponse> getAllUserOfAdmin();


    List<AddressDto> getAddressOfCurrentUser(UserDetailsImpl currentUser);

    List<AddressDto> getAddressOfUserId(Integer id);

    List<UsersAllowSendingMessageDto> getMessageSendSettingsOfUserId(Integer id);

    List<MessageDto> getAllMessagesSendOftUserId(Integer id);

    String addAdminIfWantToGetMessages(UsersAllowSendingMessageDto usersAllowSendingMessageDto);

    List<MessageDto> getAllMessagesSendOfCurrentUser(UserDetailsImpl currentUser);
}
