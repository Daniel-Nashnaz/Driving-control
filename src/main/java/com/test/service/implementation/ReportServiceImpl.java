package com.test.service.implementation;

import com.test.dto.TripSummaryDto;
import com.test.dto.UserInfoResponse;
import com.test.dto.UsersAllowSendingMessageDto;
import com.test.entity.Message;
import com.test.mapper.ActionsMapper;
import com.test.mapper.AuthMapper;
import com.test.repository.*;
import com.test.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final TravelRepository travelRepository;
    private final UserVsAdminRepository userVsAdminRepository;
    private final UsersAllowSendingMessageRepository usersAllowSendingMessageRepository;


    public List<UsersAllowSendingMessageDto> getLevelAndReportAllow(int level) {
        return usersAllowSendingMessageRepository.getUsersAllowSendingMessageByReportsIsTrueAndAlertLevel(level).
                stream().map(usersAllowSendingMessage -> ActionsMapper.usersAllowSendingMessageToDto(usersAllowSendingMessage)).collect(Collectors.toList());
    }

    public List<UserInfoResponse> findAllUsersOfAdmin(int adminID) {
        return userVsAdminRepository.findUsersByAdministratorId(adminID).stream().map(user -> AuthMapper.mapperUserVsAdminToDTO(user)).collect(Collectors.toList());


    }

    public List<TripSummaryDto> findByUserIDAllTravelThatWereAccordingToTime(Integer userid, Instant minus) {
        return travelRepository.getTripStatisticsByUserIDAndStartDate(userid, minus);

    }

    @Override
    public void createMessageAndSaveInTable(List<TripSummaryDto> tripDetails, String fullName, int adminId) {
        String subject = "Trip Report for Driver: " + fullName;
        String body = generateTripReport(tripDetails, fullName);
        Message message = new Message();
        message.setUserID(userRepository.findById(adminId).get());
        message.setSubject(subject);
        message.setBody(body);
        messageRepository.save(message);
    }
}
