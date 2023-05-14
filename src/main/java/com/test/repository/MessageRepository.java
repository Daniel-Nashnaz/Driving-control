package com.test.repository;

import com.test.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {


    @Transactional()
    List<Message> findByUserID_Id(Integer userId);

    List<Message> getMessagesByUserID_IdAndSentTimeIsNotNull(Integer userId);
}