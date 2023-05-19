package com.test.mapper;

import com.test.dto.UserInfoResponse;
import com.test.entity.UserVsAdmin;

import java.util.List;
import java.util.stream.Collectors;

public class AuthMapper {

    /*public static Users mapToEvent(RegistrationDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .type(eventDto.getType())
                .photoUrl(eventDto.getPhotoUrl())
                .createdOn(eventDto.getCreatedOn())
                .updatedOn(eventDto.getUpdatedOn())
                .club(eventDto.getClub())
                .build();
    }

    public static EventDto mapToEventDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .type(event.getType())
                .photoUrl(event.getPhotoUrl())
                .createdOn(event.getCreatedOn())
                .updatedOn(event.getUpdatedOn())
                .club(event.getClub())
                .build();
    }
}
*/

    public static UserInfoResponse mapperUserVsAdminToDTO(UserVsAdmin user) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setId(user.getUserID().getId());
        userInfoResponse.setUsername(user.getUserID().getUserName());
        userInfoResponse.setEmail(user.getUserID().getEmail());
        userInfoResponse.setFullName(user.getUserID().getFullName());
        userInfoResponse.setPhone(user.getUserID().getPhone());
        List<String> roleNameStream = user.getUserID().getUserVsRoles().stream().map(userVsRole -> userVsRole.getRoleID().getRuleName().toString()).collect(Collectors.toList());
        userInfoResponse.setRoles(roleNameStream);
        return userInfoResponse;
    }


}
