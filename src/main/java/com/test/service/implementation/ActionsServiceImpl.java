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
import org.springframework.data.domain.PageRequest;
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
    private final UserPasswordRepository userPasswordRepository;


    @Override
    public String adminAddUser(RegistrationDto registrationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserVsAdmin userVsAdmin = new UserVsAdmin();

        Users newUser = authMethods.register(registrationDto, RoleName.ROLE_USER);
        userVsAdmin.setUserID(newUser);
        userVsAdmin.setAdministratorID(userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get());
        userVsAdminRepository.save(userVsAdmin);

        Message message = new Message();
        message.setUserID(newUser);
        message.setSubject("Welcome to driving control system!");
        message.setBody("This is tour userName: " + registrationDto.getUserName() + "\n" +
                "This is your email: " + registrationDto.getEmail() + "\n" +
                "This is your password: " + registrationDto.getPassword());
        messageRepository.save(message);
        return "User successfully added and account details sent to him!";
    }

    @Override
    public ResponseCookie updateUser(UpdateCurrentUserDto updateCurrentUserDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!((UserDetailsImpl) auth.getPrincipal()).getEmail().equals(updateCurrentUserDto.getEmail())){
        authMethods.isNotExsist(updateCurrentUserDto.getEmail(), updateCurrentUserDto.getEmail());
        }
        if(!((UserDetailsImpl) auth.getPrincipal()).getUsername().equals(updateCurrentUserDto.getUsername())){
            authMethods.isNotExsist(updateCurrentUserDto.getUsername(), updateCurrentUserDto.getUsername());
        }
        Users userUpdate = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get();
        userUpdate.setUserName(updateCurrentUserDto.getUsername());
        userUpdate.setEmail(updateCurrentUserDto.getEmail());
        userUpdate.setFullName(updateCurrentUserDto.getFullName());
        userUpdate.setPhone(updateCurrentUserDto.getPhone());
        Address addressByUser = addressRepository.getAddressByUserID_Id(userUpdate.getId()).get(0);
        addressByUser.setAddress(updateCurrentUserDto.getAddress());
        addressByUser.setApartmentNumber(updateCurrentUserDto.getApartmentNumber());
        addressByUser.setCity(updateCurrentUserDto.getCity());
        addressByUser.setCountry(updateCurrentUserDto.getCountry());
        addressByUser.setUserID(userUpdate);
        userUpdate.setAddresses(Collections.singletonList(addressByUser));
        ((UserDetailsImpl) auth.getPrincipal()).setEmail(updateCurrentUserDto.getEmail());
        ((UserDetailsImpl) auth.getPrincipal()).setUsername(updateCurrentUserDto.getUsername());
        ((UserDetailsImpl) auth.getPrincipal()).setFullName(updateCurrentUserDto.getFullName());
        ((UserDetailsImpl) auth.getPrincipal()).setPhone(updateCurrentUserDto.getPhone());

        userRepository.save(userUpdate);
        return jwtUtils.generateJwtCookie(((UserDetailsImpl) auth.getPrincipal()));


    }

    @Override
    public String updatePasswordOfCurrentUser(UpdatePasswordDto updatePasswordDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(passwordEncoder.matches(updatePasswordDto.getCurrentPassword(),((UserDetailsImpl) auth.getPrincipal()).getPassword())){
            Users userUpdate = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).get();
            authMethods.getAllPassAndCheckIfUsedBefore(userUpdate, updatePasswordDto.getNewPassword());
            String pass = passwordEncoder.encode(updatePasswordDto.getNewPassword());
            authMethods.setAllPassNotActive(userUpdate);
            UserPassword userPassword = new UserPassword();
            userPassword.setPassword(pass);
            userPassword.setIsActive(true);
            userPassword.setUserID(userUpdate);
            userUpdate.setUserPasswords(Collections.singleton(userPassword));
            ((UserDetailsImpl) auth.getPrincipal()).setPassword(pass);
            userPasswordRepository.save(userPassword);
            return "Password updated successfully!";
        }else {
        return "Current password not match!";
        }

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
        List<Message> messagesByUserId = messageRepository.getRecentMessagesByUserID(
                currentUser.getId(),
                PageRequest.of(0, 50)
        );
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
