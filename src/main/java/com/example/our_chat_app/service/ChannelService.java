package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.ChannelDto;
import com.example.our_chat_app.entity.Group;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.projection.ChannelProjection;
import com.example.our_chat_app.projection.PostProjection;
import com.example.our_chat_app.repository.ChannelRepository;
import com.example.our_chat_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    UserRepository userRepository;


    public List<ChannelProjection> getAllChannels() {
        return channelRepository.getAllChannels();
    }

    public List<PostProjection> getAllPosts() {
        return channelRepository.getAllPosts();
    }


    public HttpEntity<?> createChannel(Long userid, ChannelDto channelDto) {
        Group channel = new Group();
        channel.setName(channelDto.getChannelName());
        channel.setUsername(channelDto.getChannelUsername());
        channel.setPrivate(channelDto.getIsPrivate());
        channel.setCreatedBy(userRepository.getById(userid));
        channel.setChannel(true);
        Group newChannel = channelRepository.save(channel);

        ApiResponse response = new ApiResponse("success", true, newChannel);
        return ResponseEntity.ok(response);

    }

}
