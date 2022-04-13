package com.example.our_chat_app.service;

import com.example.our_chat_app.projection.ChannelProjection;
import com.example.our_chat_app.projection.PostProjection;
import com.example.our_chat_app.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    @Autowired
    ChannelRepository channelRepository;


    public List<ChannelProjection> getAllChannels() {
        return channelRepository.getAllChannels();
    }

    public List<PostProjection> getAllPosts() {
        return channelRepository.getAllPosts();
    }

}
