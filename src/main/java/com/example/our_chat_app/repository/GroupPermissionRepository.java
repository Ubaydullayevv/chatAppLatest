package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.GroupsPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupPermissionRepository extends JpaRepository<GroupsPermissions, Long> {
    Optional<GroupsPermissions> findByUserIdAndGroupId(Long userId,Long groupId);
}
