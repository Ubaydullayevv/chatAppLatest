package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.Group;
import com.example.our_chat_app.projetion.GroupProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query(
            nativeQuery = true,
            value = "select * from groups"
    )
    List<GroupProjection> showAllGroups(Long afishaId);

    @Query(nativeQuery = true,
            value = "select count(u.id)>0 from users u\n" +
            "join groups_users gu on u.id = gu.users_id\n" +
            "join groups g on gu.groups_id = g.id\n" +
            "where u.id = :from and g.id = :groupId")
    boolean checkUser(Long from, Long groupId);

}
