package com.example.our_chat_app.projetion;


import com.example.our_chat_app.entity.ChatRoom;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = ChatRoom.class)

public interface ChatProjection {

    Long getChatId();

    Long getAvatarId();

    String getFullName();

    String getLastMessageText();
}
