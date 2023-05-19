package com.test.service.implementation;

import com.test.dto.*;
import com.test.entity.*;
import com.test.exception.AuthApiException;
import com.test.mapper.ActionsMapper;
import com.test.mapper.AuthMapper;
import com.test.repository.*;
import com.test.security.jwt.JwtUtils;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.ActionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    private final AddressRepository addressRepository;
    private final UsersAllowSendingMessageRepository usersAllowSendingMessageRepository;
    private final MessageRepository messageRepository;


    @Override
    public String adminAddUser(RegistrationDto registrationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserVsAdmin userVsAdmin = new UserVsAdmin();

        userVsAdmin.setUserID(authMethods.register(registrationDto, RoleName.ROLE_USER));

        userVsAdmin.setAdministratorID(userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get());

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


    public String updateUserById(Integer id, UpdateUser updateUser) {
        authMethods.isNotExsist(updateUser.getUserName(), updateUser.getEmail());
        Users userUpdate = userRepository.findById(id).get();
        userUpdate.setUserName(updateUser.getUserName());
        userUpdate.setPhone(updateUser.getPhone());
        userUpdate.setFullName(updateUser.getFullName());
        userUpdate.setEmail(updateUser.getEmail());
        userRepository.save(userUpdate);
        return "User updated successfully!";

    }

    @Override
    public void deleteById(Integer id) {
        Optional<Users> userDelete = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new AuthApiException(HttpStatus.BAD_REQUEST, "user not found.")));
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
        return usersByAdministratorId.stream().map(user -> AuthMapper.mapperUserVsAdminToDTO(user)).collect(Collectors.toList());
    }

    @Override
    public List<AddressDto> getAddressOfCurrentUser(UserDetailsImpl currentUser) {
        List<Address> addressByUserID = addressRepository.getAddressByUserID_Id(currentUser.getId());
        return addressByUserID.stream().map(address -> ActionsMapper.addressToDto(address)).collect(Collectors.toList());

    }


    @Override
    public List<MessageDto> getAllMessagesSendOfCurrentUser(UserDetailsImpl currentUser) {
        List<Message> messagesByUserId = messageRepository.getMessagesByUserID_IdAndSentTimeIsNotNull(currentUser.getId());
        return messagesByUserId.stream().map(message -> ActionsMapper.MessagesToDto(message)).collect(Collectors.toList());

    }


    @Override
    public List<MessageDto> getAllMessagesSendOftAdminId(Integer id) {
        List<Message> messagesByUserId = messageRepository.getMessagesByUserID_IdAndSentTimeIsNotNull(id);
        return messagesByUserId.stream().map(message -> ActionsMapper.MessagesToDto(message)).collect(Collectors.toList());

    }

    @Override
    public String addAdminIfWantToGetMessages(UsersAllowSendingMessageDto usersAllowSendingMessageDto) {
        UsersAllowSendingMessage usersAllowSendingMessage = new UsersAllowSendingMessage();
        usersAllowSendingMessage.setAlertLevel(usersAllowSendingMessageDto.getAlertLevel());
        usersAllowSendingMessage.setForwardDirections(usersAllowSendingMessageDto.getForwardDirections());
        usersAllowSendingMessage.setLaneDeparture(usersAllowSendingMessageDto.getLaneDeparture());
        usersAllowSendingMessage.setSuddenBraking(usersAllowSendingMessageDto.getSuddenBraking());
        usersAllowSendingMessage.setExceededSpeedLimit(usersAllowSendingMessageDto.getExceededSpeedLimit());
        usersAllowSendingMessage.setPedestrianAndCyclistCollision(usersAllowSendingMessageDto.getPedestrianAndCyclistCollision());
        if (usersAllowSendingMessageDto.getUserId() == 0 || usersAllowSendingMessageDto.getUserId() == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            usersAllowSendingMessage.setUserID(userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get());
        } else {
            usersAllowSendingMessage.setUserID(userRepository.findById(usersAllowSendingMessageDto.getUserId()).get());
        }
        usersAllowSendingMessageRepository.save(usersAllowSendingMessage);
        return "setting added!";

    }

    @Override
    public String updateAllowMessageOfAdmin(UsersAllowSendingMessageDto usersAllowSendingMessageDto) {
        UsersAllowSendingMessage usersAllowSendingMessage;
        if (usersAllowSendingMessageDto.getUserId() == 0) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            usersAllowSendingMessage = usersAllowSendingMessageRepository.getUsersAllowSendingMessageByUserID_Id(((UserDetailsImpl) auth.getPrincipal()).getId()).get(0);
        } else {
            usersAllowSendingMessage = usersAllowSendingMessageRepository.getUsersAllowSendingMessageByUserID_Id((usersAllowSendingMessageDto.getUserId())).get(0);
        }
        usersAllowSendingMessage.setAlertLevel(usersAllowSendingMessageDto.getAlertLevel());
        usersAllowSendingMessage.setForwardDirections(usersAllowSendingMessageDto.getForwardDirections());
        usersAllowSendingMessage.setLaneDeparture(usersAllowSendingMessageDto.getLaneDeparture());
        usersAllowSendingMessage.setSuddenBraking(usersAllowSendingMessageDto.getSuddenBraking());
        usersAllowSendingMessage.setExceededSpeedLimit(usersAllowSendingMessageDto.getExceededSpeedLimit());
        usersAllowSendingMessage.setPedestrianAndCyclistCollision(usersAllowSendingMessageDto.getPedestrianAndCyclistCollision());
        usersAllowSendingMessageRepository.save(usersAllowSendingMessage);
        return "setting updated!";
    }

    @Override
    public List<UsersAllowSendingMessageDto> getMessageSendSettingsOfAdminId(Integer id) {
        List<UsersAllowSendingMessage> messageAllowOfUserID = usersAllowSendingMessageRepository.getUsersAllowSendingMessageByUserID_Id(id);
        return messageAllowOfUserID.stream().map(usersAllowSendingMessage -> ActionsMapper.usersAllowSendingMessageToDto(usersAllowSendingMessage)).collect(Collectors.toList());

    }

    @Override
    public List<AddressDto> getAddressOfUserId(Integer id) {
        List<Address> addressByUserId = addressRepository.getAddressByUserID_Id(id);
        return addressByUserId.stream().map(address -> ActionsMapper.addressToDto(address)).collect(Collectors.toList());

    }


    @Override
    public String deleteCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        deleteById((((UserDetailsImpl) auth.getPrincipal()).getId()));
        return "User deleted successfully!";
    }


}
