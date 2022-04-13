package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {
}
