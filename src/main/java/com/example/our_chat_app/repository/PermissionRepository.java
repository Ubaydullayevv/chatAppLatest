package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.Permission;
import com.example.our_chat_app.entity.enums.PermissionEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermissionEnum(PermissionEnum permissionEnum);

    Optional<Permission> findByName(String name);
}
