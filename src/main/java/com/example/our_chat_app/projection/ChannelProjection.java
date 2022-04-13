package com.example.our_chat_app.projection;

import com.example.our_chat_app.entity.Group;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;

@Projection(types = Group.class)
public interface ChannelProjection {

    Long getChannelId();
    String getChannelName();
    String getChannelLastPost();
    Timestamp getLastPostTime();


}
