package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.ChannelDto;
import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.dto.MessageDto;
import com.example.our_chat_app.entity.Group;
import com.example.our_chat_app.entity.GroupMessage;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.projection.ChannelProjection;
import com.example.our_chat_app.projection.PostProjection;
import com.example.our_chat_app.repository.ChannelRepository;
import com.example.our_chat_app.repository.GroupMessageRepository;
import com.example.our_chat_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChannelService {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupMessageRepository groupMessageRepository;


    public List<?> getAllChannels(Long userId) {
        return channelRepository.getAllChannels(userId);
    }

    public List<?> getAllPosts(Long channelId) {
        return channelRepository.getAllPosts(channelId);
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

    public HttpEntity<?> writePost(Long userid, GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setText(groupMessageDto.getMessage());
        groupMessage.setFrom(userRepository.findById(userid).get());
        groupMessage.setId(groupMessageDto.getGroupId());
        GroupMessage message = groupMessageRepository.save(groupMessage);

        ApiResponse response = new ApiResponse("success", true, message);
        return ResponseEntity.ok(response);

    }


    public HttpEntity<?> deletePost(Long postId) {
        channelRepository.deleteById(postId);
        ApiResponse response = new ApiResponse("Successfully deleted!", true);
        return ResponseEntity.ok(response);
    }
}
