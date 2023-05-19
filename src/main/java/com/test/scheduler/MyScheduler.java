package com.test.scheduler;

import com.test.dto.TripSummaryDto;
import com.test.dto.UserInfoResponse;
import com.test.dto.UsersAllowSendingMessageDto;
import com.test.service.implementation.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyScheduler {

    private final ReportServiceImpl reportService;


    //every day in 4PM
    @Scheduled(cron = "0 00 16 * * *")
    public void test() {
        System.out.println("send....");
    }

    //high level
    // every five minute
    //@Scheduled(cron = "0 */5 * * * *")
    public void startCreatingReportsAdminFiveMinute() {
        startCreatingReport(3,Instant.now().minus(Duration.ofMinutes(5)));
    }


    //numeral level
    // every day in 7 PM
    @Scheduled(cron = "0 0 19 * * *")
    public void startCreatingReportsAdminEveryEndOfDay() {
        startCreatingReport(2,Instant.now().minus(Duration.ofDays(10)));
    }

    //low level
    //every friday in 12 afternoon
    @Scheduled(cron = "0 0 12 ? * FRI")
    public void startCreatingReportsAdminEveryWeekend() {

       startCreatingReport(1,Instant.now().minus(Duration.ofDays(7)));

    }

    private void startCreatingReport(int level,Instant days){
        List<UsersAllowSendingMessageDto> allUserAllow = reportService.getLevelAndReportAllow(level);
        if (allUserAllow.isEmpty()) {
            return;
        }
        for (UsersAllowSendingMessageDto admin :
                allUserAllow) {
            List<UserInfoResponse> allUsersOfAdmin = reportService.findAllUsersOfAdmin(admin.getUserId());
            for (UserInfoResponse user :
                    allUsersOfAdmin) {
                List<TripSummaryDto> tripDetails = reportService.findByUserIDAllTravelThatWereAccordingToTime(user.getId(),days );
                if (tripDetails.isEmpty()){
                    System.out.println("No Message!!!");
                    continue;
                }
                reportService.createMessageAndSaveInTable(tripDetails, user.getUsername(), admin.getUserId());
            }

        }
    }




}