package com.test.repository;

import com.test.entity.UsersAllowSendingMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersAllowSendingMessageRepository extends JpaRepository<UsersAllowSendingMessage, Integer> {

    //@Query("SELECT u FROM Users u JOIN FETCH u.usersAllowSendingMessage WHERE u.id = :userId")
    List<UsersAllowSendingMessage> getUsersAllowSendingMessageByUserID_Id(Integer userId);
    List<UsersAllowSendingMessage> getUsersAllowSendingMessageByReportsIsTrueAndAlertLevel(Integer alertLevel);
    // List<UsersAllowSendingMessage> findByUserID_Id(Integer id);
    // @Transactional
    //List<UsersAllowSendingMessage> getUsersAllowSendingMessageByUserID_Id(Integer userID);

//    @Transactional(readOnly = true)
//    List<UsersAllowSendingMessage> findByUserID_Id(Integer userId);
}