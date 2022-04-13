package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.ChatRoomMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessagesRepository extends JpaRepository<ChatRoomMessage, Long> {

    @Query(nativeQuery = true,
            value = "select coalesce((select\n" +
            "     case when from_id = :from then delete_from = false when from_id <> :from then delete_from = true else false end\n" +
            "from chat_messages\n" +
            "where id = :messageId), false)")
    boolean checkDeletion(Long messageId, Long from);

    @Query(nativeQuery = true,
            value = "select coalesce((select (:from in(cr.user1_id, cr.user2_id)) as checkbool from chat_messages cm\n" +
                    "join chat_rooms cr on cm.chat_room_id = cr.id\n" +
                    "where cm.id = :id), false)")
    boolean checkUserCanDelete(Long from, Long id);
}
