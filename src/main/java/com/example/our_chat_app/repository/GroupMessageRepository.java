package com.example.our_chat_app.repository;

import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.entity.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {
    @Query(nativeQuery = true, value = "select * from group_message gm where gm.from_id = :userId")
    GroupMessage getGroupMessageByUserId(Long userId);
}
