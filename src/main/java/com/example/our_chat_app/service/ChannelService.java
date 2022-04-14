package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.ChannelDto;
import com.example.our_chat_app.dto.GroupDto;
import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.dto.MessageDto;
import com.example.our_chat_app.entity.Group;
import com.example.our_chat_app.entity.GroupMessage;
import com.example.our_chat_app.entity.GroupsPermissions;
import com.example.our_chat_app.entity.enums.PermissionEnum;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.projection.ChannelProjection;
import com.example.our_chat_app.projection.PostProjection;
import com.example.our_chat_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    PermissionRepository permissionRepository;

    @Autowired
    GroupPermissionRepository groupPermissionRepository;

    @Autowired
    GroupMessageRepository groupMessageRepository;

    @Autowired
    UserService userService;


    public List<?> getAllChannels(Long userId) {
        return channelRepository.getAllChannels(userId);
    }

    public Page<?> getAllPosts(Long channelId, int page, int size) {
        return channelRepository.getAllPosts(channelId, PageRequest.of(page, size));
    }


    public HttpEntity<?> createChannel(Long userid, GroupDto channelDto) {
        Group channel = new Group();
        channel.setName(channelDto.getName());
        channel.setUsername(channelDto.getUsername());
        channel.setPrivate(channelDto.getIsPrivate());
        channel.setCreatedBy(userRepository.getById(userid));
        channel.setChannel(true);

        try {
            channelDto.getUsers().remove(userid);
            channel.setUsers(userRepository.findAllById(channelDto.getUsers()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<Long> usersIds = channelDto.getUsers();
        channel.addUser(userRepository.findById(userid).get());
        Group newChannel = channelRepository.save(channel);
        for (Long usersId : usersIds) {
            GroupsPermissions groupsPermissions = new GroupsPermissions();
            groupsPermissions.setGroup(newChannel);
            groupsPermissions.setUser(userRepository.findById(usersId).get());
            groupsPermissions.setPermission(permissionRepository.findByPermissionEnum(PermissionEnum.MEMBER).get());
            groupPermissionRepository.save(groupsPermissions);
        }
        GroupsPermissions groupsPermissions = new GroupsPermissions();
        groupsPermissions.setGroup(newChannel);
        groupsPermissions.setUser(userRepository.findById(userid).get());
        groupsPermissions.setPermission(permissionRepository.findByPermissionEnum(PermissionEnum.OWNER).get());
        groupPermissionRepository.save(groupsPermissions);
        ApiResponse response = new ApiResponse("success", true, channel.getId());
        return ResponseEntity.ok(response);

    }

    public HttpEntity<?> writePost(Long userid, GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setText(groupMessageDto.getMessage());
        groupMessage.setFrom(userRepository.findById(userid).get());
        groupMessage.setGroup(channelRepository.findById(groupMessageDto.getGroupId()).get());
        groupMessageRepository.save(groupMessage);

        ApiResponse response = new ApiResponse("success", true, null);
        return ResponseEntity.ok(response);

    }


    public HttpEntity<?> deletePost(Long postId) {
        groupMessageRepository.deleteById(postId);
        ApiResponse response = new ApiResponse("Successfully deleted!", true);
        return ResponseEntity.ok(response);
    }
}
