package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.Group;
import com.example.our_chat_app.projetion.GroupProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {


    @Query(nativeQuery = true,
            value = "select count(u.id)>0 from users u\n" +
            "join groups_users gu on u.id = gu.user_id\n" +
            "join groups g on gu.group_id = g.id\n" +
            "where u.id = :from and g.id = :groupId")
    boolean checkUser(Long from, Long groupId);
    @Query(
            nativeQuery = true,
            value = "select g.id,\n" +
                    "       g.name,\n" +
                    "       (select text from group_message where group_message.group_id = g.id order by created_at desc limit 1),\n" +
                    "       max(gm.created_at) as data,\n" +
                    "       g.group_avatar_id\n" +
                    "from groups g\n" +
                    "         join groups_users gu on g.id = gu.group_id\n" +
                    "         join group_message gm on g.id = gm.group_id\n" +
                    "group by g.id, g.name"
    )
    List<Map<String, Object>> showAllGroups(Long afishaId);

}
