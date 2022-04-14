package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                    "       (select text as lastMessage from group_message where group_message.group_id = g.id order by created_at desc limit 1),\n" +
                    "       max(gm.created_at) as data,\n" +
                    "       g.group_avatar_id\n" +
                    "from groups g\n" +
                    "join groups_users gu on g.id = gu.group_id\n" +
                    "join group_message gm on g.id = gm.group_id\n" +
                    "         join groups_users gu on g.id = gu.group_id\n" +
                    "         join group_message gm on g.id = gm.group_id\n" +
                    "where gu.user_id=:userId and g.is_channel=false\n" +
                    "group by g.id, g.name"
    )
    List<Map<String, Object>> showAllGroups(Long userId);
    @Query(nativeQuery = true,
            value = "select gm.id as id,\n" +
                    "       gm.text,\n" +
                    "       concat(u.firstname, '', u.lastname) as authorName,\n" +
                    "       gm.view_count,\n" +
                    "       (case when view_count < 2 then false else true end ) as \"isRead\",\n" +
                    "       gm.created_at,\n" +
                    "       gm.updated_at,\n" +
                    "        (case when updated_at isnull then false else true end ) as \"isEdited\"\n" +
                    "\n" +
                    "from group_message gm\n" +
                    "         join groups g on g.id = gm.group_id\n" +
                    "         join users u on u.id = gm.from_id\n" +
                    "where g.id = :groupId\n" +
                    "order by gm.created_at desc")
    Page<Map<String,Object>> getAllMessage(Long groupId, Pageable pageable);


}
