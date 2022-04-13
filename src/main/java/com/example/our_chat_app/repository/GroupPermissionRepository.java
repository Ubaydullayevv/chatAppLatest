package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.GroupsPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPermissionRepository extends JpaRepository<GroupsPermissions, Long> {
}
