package com.example.our_chat_app.repository;

import com.example.our_chat_app.dto.MessageDto;
import com.example.our_chat_app.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query(value = "select :page                                                                                 as page,\n" +
            "       :size                                                                                 as size,\n" +
            "       sub_in.roomId                                                                         as \"chatRoomId\",\n" +
            "       sub_in.user1_id                                                                       as user1Id,\n" +
            "       sub_in.user2_id                                                                       as user2Id,\n" +
            "       (select count(distinct c.id)\n" +
            "        from chat_rooms\n" +
            "                 join chat_messages c on chat_rooms.id = c.chat_room_id\n" +
            "        where (chat_rooms.user2_id in (:from, :to) and chat_rooms.user1_id in (:from, :to))) as \"elementCount\",\n" +
            "       cast(json_agg(json_build_object('date', date, 'messages', messages)) as text)         as \"messages\",\n" +
            "       user2_id\n" +
            "from (select date,\n" +
            "             json_agg(json_build_object('msgId', inn.msgId, 'msgBody', msgBody, 'msgFromId', msgFromId, 'writtenTime',\n" +
            "                                        inn.writtinTime)) as messages,\n" +
            "             user1_id,\n" +
            "             user2_id,\n" +
            "             inn.roomId\n" +
            "      from (select user2_id,\n" +
            "                   user1_id,\n" +
            "                   chat_rooms.id               as roomId,\n" +
            "                   cm.id                       as msgId,\n" +
            "                   cast(cm.created_at as time) as writtinTime,\n" +
            "                   cast(cm.created_at as date) as date,\n" +
            "                   cm.text                     as msgBody,\n" +
            "                   cm.from_id                  as msgFromId\n" +
            "            from chat_rooms\n" +
            "                     join chat_messages cm on chat_rooms.id = cm.chat_room_id\n" +
            "            where (chat_rooms.user2_id in (:from, :to) and chat_rooms.user1_id in (:from, :to))\n" +
            "              and ((cm.from_id = :from and cm.delete_from = false) or (cm.from_id = :to and cm.delete_from = true) or\n" +
            "                   cm.delete_from isnull)\n" +
            "            order by created_at desc\n" +
            "            limit :size offset :size * :page) as inn\n" +
            "      group by inn.date, user2_id, user1_id, roomId) as sub_in\n" +
            "group by user1_id, user2_id, sub_in.roomId", nativeQuery = true)
    Map<String, Object> findAllMessagesPage(Long to, Long from, int size, int page);

    @Modifying
    @Query(nativeQuery = true,
            value = "update chat_messages\n" +
                    "set is_read = true\n" +
                    "where id in\n" +
                    "      (select cm.id\n" +
                    "       from chat_rooms\n" +
                    "                join chat_messages cm on chat_rooms.id = cm.chat_room_id\n" +
                    "       where (chat_rooms.user2_id in (:from, :to) and chat_rooms.user1_id in (:from, :to))\n" +
                    "       order by created_at desc\n" +
                    "       limit :size offset :size * :page)")
    void checkMessageRead(Long to, Long from, int page, int size);


    boolean existsByUser1IdAndUser2Id(Long user1_id, Long user2_id);

    Optional<ChatRoom> findByUser1IdAndUser2Id(Long user1_id, Long user2_id);

    @Query(nativeQuery = true,
            value = "select cr.id                                                                                       as \"chatId\",\n" +
                    "       (select concat(u2.firstname, ' ', u2.lastname)\n" +
                    "        from users u2\n" +
                    "        where case when cr.user1_id = :from then cr.user2_id = u2.id else cr.user1_id = u2.id end) as \"name\",\n" +
                    "       count(distinct cm.id)                                                                       as \"newMessageCount\",\n" +
                    "       'chatRoom'                                                                                  as \"type\",\n" +
                    "       (select cm1.text\n" +
                    "        from chat_messages cm1\n" +
                    "        where cm1.chat_room_id = cr.id\n" +
                    "        order by created_at desc\n" +
                    "        limit 1)                                                                                   as \"lastMessage\",\n" +
                    "       max(cm.created_at)                                                                          as \"lastMessageTime\",\n" +
                    "       (select a2.id\n" +
                    "        from users u2\n" +
                    "                 join attachments a2 on u2.avatar_id = a2.id\n" +
                    "        where case when cr.user1_id = :from then cr.user2_id = u2.id else cr.user1_id = u2.id end) as \"imageId\"\n" +
                    "from chat_rooms cr\n" +
                    "         join chat_messages cm on cr.id = cm.chat_room_id\n" +
                    "         join users u on cr.user1_id = u.id or cr.user2_id = u.id\n" +
                    "         left join attachments a on u.avatar_id = a.id\n" +
                    "where u.id = :from and  (select count(distinct m.id)>0 from chat_messages m where m.chat_room_id = cr.id group by cr.id)\n" +
                    "group by cr.id\n" +
                    "union\n" +
                    "select gr.id                                                  as \"chatId\",\n" +
                    "       gr.name                                                as \"name\",\n" +
                    "       count(distinct gm.id)                                  as \"newMessageCount\",\n" +
                    "       case when gr.is_channel then 'chanel' else 'group' end as \"type\",\n" +
                    "       (select grm.text\n" +
                    "        from group_message grm\n" +
                    "        where grm.group_id = gr.id\n" +
                    "        order by grm.created_at desc\n" +
                    "        limit 1)                                              as \"lastMessage\",\n" +
                    "       max(gm.created_at)                                     as \"lastMessageTime\",\n" +
                    "       a.id                                                   as \"imageId\"\n" +
                    "from groups gr\n" +
                    "         join group_message gm on gr.id = gm.group_id\n" +
                    "         join groups_users gu on gr.id = gu.group_id\n" +
                    "         join users u on gm.from_id = u.id\n" +
                    "         left join attachments a on gr.group_avatar_id = a.id\n" +
                    "where :from in (gu.user_id) and (select count(distinct m.id)>0 from group_message m where m.group_id = gr.id group by gr.id)\n" +
                    "group by gr.id, a.id\n" +
                    "order by \"lastMessageTime\" desc")
    List<Map<String, Object>> getAllUnreadMsg(Long from);

    @Query(nativeQuery = true,
            value = "select cr.id                                                                                       as \"chatId\",\n" +
                    "       (select concat(u2.firstname, ' ', u2.lastname)\n" +
                    "        from users u2\n" +
                    "        where case when cr.user1_id = :from then cr.user2_id = u2.id else cr.user1_id = u2.id end) as \"name\",\n" +
                    "       count(distinct cm.id)                                                                       as \"newMessageCount\",\n" +
                    "       'chatRoom'                                                                                  as \"type\",\n" +
                    "       (select cm1.text\n" +
                    "        from chat_messages cm1\n" +
                    "        where cm1.chat_room_id = cr.id\n" +
                    "        order by created_at desc\n" +
                    "        limit 1)                                                                                   as \"lastMessage\",\n" +
                    "       max(cm.created_at)                                                                          as \"lastMessageTime\",\n" +
                    "       (select a2.id\n" +
                    "        from users u2\n" +
                    "                 join attachments a2 on u2.avatar_id = a2.id\n" +
                    "        where case when cr.user1_id = :from then cr.user2_id = u2.id else cr.user1_id = u2.id end) as \"imageId\"\n" +
                    "from chat_rooms cr\n" +
                    "         join chat_messages cm on cr.id = cm.chat_room_id\n" +
                    "         join users u on cr.user1_id = u.id or cr.user2_id = u.id\n" +
                    "         left join attachments a on u.avatar_id = a.id\n" +
                    "where u.id = :from\n" +
                    "group by cr.id\n" +
                    "union\n" +
                    "select gr.id                                                  as \"chatId\",\n" +
                    "       gr.name                                                as \"name\",\n" +
                    "       count(distinct gm.id)                                  as \"newMessageCount\",\n" +
                    "       case when gr.is_channel then 'chanel' else 'group' end as \"type\",\n" +
                    "       (select grm.text\n" +
                    "        from group_message grm\n" +
                    "        where grm.group_id = gr.id\n" +
                    "        order by grm.created_at desc\n" +
                    "        limit 1)                                              as \"lastMessage\",\n" +
                    "       max(gm.created_at)                                     as \"lastMessageTime\",\n" +
                    "       a.id                                                   as \"imageId\"\n" +
                    "from groups gr\n" +
                    "         join group_message gm on gr.id = gm.group_id\n" +
                    "         join groups_users gu on gr.id = gu.group_id\n" +
                    "         join users u on gm.from_id = u.id\n" +
                    "         left join attachments a on gr.group_avatar_id = a.id\n" +
                    "where :from in (gu.user_id)\n" +
                    "group by gr.id, a.id\n" +
                    "order by \"lastMessageTime\" desc")
    List<Map<String, Object>> getAllChats(Long from);


    @Query(nativeQuery = true, value = "select cr.id                                                                                       as \"chatId\",\n" +
            "       (select concat(u2.firstname, ' ', u2.lastname)\n" +
            "        from users u2\n" +
            "        where case when cr.user1_id = :from then cr.user2_id = u2.id else cr.user1_id = u2.id end) as \"name\",\n" +
            "       count(distinct cm.id)                                                                       as \"newMessageCount\",\n" +
            "       'chatRoom'                                                                                  as \"type\",\n" +
            "       (select cm1.text\n" +
            "        from chat_messages cm1\n" +
            "        where cm1.chat_room_id = cr.id\n" +
            "        order by created_at desc\n" +
            "        limit 1)                                                                                   as \"lastMessage\",\n" +
            "       max(cm.created_at)                                                                          as \"lastMessageTime\",\n" +
            "       (select a2.id\n" +
            "        from users u2\n" +
            "                 join attachments a2 on u2.avatar_id = a2.id\n" +
            "        where case when cr.user1_id = :from then cr.user2_id = u2.id else cr.user1_id = u2.id end) as \"imageId\"\n" +
            "from chat_rooms cr\n" +
            "         join chat_messages cm on cr.id = cm.chat_room_id\n" +
            "         join users u on cr.user1_id = u.id or cr.user2_id = u.id\n" +
            "         left join attachments a on u.avatar_id = a.id\n" +
            "where u.id = :from\n" +
            "group by cr.id\n")
    List<Map<String, Object>> getAllUserChatsById(Long from);


    @Query(nativeQuery = true, value = "select * from chat_messages")
    void editMessage(MessageDto messageDto);


//    @Query(nativeQuery = true, value = "")
//    Map<String, Object> getChatById(Long chatRoomId);
}
