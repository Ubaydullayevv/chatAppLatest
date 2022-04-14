package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select " +
            "username, avatar_id as \"avatarId\", last_seen as \"lastSeen\" , concat(firstname,' ',lastname) as fullname from users where username like :username ")
    List<Map<String, Object>> getUserByUsernameLike1(String username);

    Optional<User> findByUsername(String username);

    @Query(value = "select p.permission_enum from users\n" +
            "join user_permissions up on users.id = up.admin_id\n" +
            "join permissions p on up.permission_id = p.id\n" +
            "where up.group_id = :groupId and users.username = :name", nativeQuery = true)
    List<String> getAuthority(String name, Long groupId);
}
