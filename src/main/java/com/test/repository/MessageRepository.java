package com.test.repository;

import com.test.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {


    @Transactional()
    List<Message> findByUserID_Id(Integer userId);

    List<Message> getMessagesByUserID_IdAndSentTimeIsNotNull(Integer userId);

    /**
     *
     * @param userId
     * @param pageable
     *  Selects certain columns from a table where the column "UserID" is equal to userId and the column "SentTime" is not empty.
     *  The results are sorted by the "SentTime" column in descending order and only the first X rows are returned.
     */
    @Transactional
    @Query("SELECT m FROM Message m WHERE m.userID.id = ?1 AND m.sentTime IS NOT NULL ORDER BY m.sentTime DESC")
    List<Message> getRecentMessagesByUserID(Integer userId, Pageable pageable);
}