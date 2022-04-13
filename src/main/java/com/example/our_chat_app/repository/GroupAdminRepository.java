package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.Attachment;
import com.example.our_chat_app.entity.GroupsAdmins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupsAdmins, Long> {

}
