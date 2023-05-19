package com.test.mapper;

import com.test.dto.AddressDto;
import com.test.dto.MessageDto;
import com.test.dto.UsersAllowSendingMessageDto;
import com.test.entity.Address;
import com.test.entity.Message;
import com.test.entity.UsersAllowSendingMessage;

public class ActionsMapper {

    public static AddressDto addressToDto(Address address) {
        AddressDto addressDto = new AddressDto()
                .setId(address.getId())
                .setUserID(address.getUserID().getId())
                .setAddress(address.getAddress())
                .setCity(address.getCity())
                .setApartmentNumber(address.getApartmentNumber())
                .setCountry(address.getCountry());
        return addressDto;
    }


    public static UsersAllowSendingMessageDto usersAllowSendingMessageToDto(UsersAllowSendingMessage usersAllowSendingMessage) {
        UsersAllowSendingMessageDto usersAllowSendingMessageDto = new UsersAllowSendingMessageDto()
                .setUserId(usersAllowSendingMessage.getUserID().getId())
                .setAlertLevel(usersAllowSendingMessage.getAlertLevel())
                .setReports(usersAllowSendingMessage.getReports())
                .setExceededSpeedLimit(usersAllowSendingMessage.getExceededSpeedLimit())
                .setLaneDeparture(usersAllowSendingMessage.getLaneDeparture())
                .setForwardDirections(usersAllowSendingMessage.getForwardDirections())
                .setSuddenBraking(usersAllowSendingMessage.getSuddenBraking())
                .setPedestrianAndCyclistCollision(usersAllowSendingMessage.getPedestrianAndCyclistCollision());
        return usersAllowSendingMessageDto;
    }

    public static MessageDto MessagesToDto(Message message) {
        MessageDto messageDto = new MessageDto()
                .setId(message.getId())
                .setUserId(message.getUserID().getId())
                .setSubject(message.getSubject())
                .setBody(message.getBody())
                .setSentTime(message.getSentTime());
        return messageDto;
    }
}