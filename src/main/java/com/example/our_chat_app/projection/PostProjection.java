package com.example.our_chat_app.projection;

import com.example.our_chat_app.entity.GroupMessage;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;

@Projection(types = GroupMessage.class)
public interface PostProjection {

    Long getPostId();
    String getPostText();
    Timestamp getPostTime();
    boolean isEdited();
    Integer view_count();

}
