package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.GroupDto;
import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.entity.*;
import com.example.our_chat_app.entity.enums.PermissionEnum;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMessageRepository messageRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    GroupAdminRepository groupAdminRepository;

    @Autowired
    GroupPermissionRepository groupPermissionRepository;



    public HttpEntity<?> createGroup(GroupDto groupDto, MultipartFile avatar, Long from) {
        ApiResponse response = new ApiResponse();
        try {

            Group group = new Group();
            group.setBio(groupDto.getBio());
            group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            group.setChannel(false);
            group.setCreatedBy(userRepository.findById(from).get());
            group.setName(groupDto.getName());
            group.setPrivate(groupDto.getIsPrivate());
            group.setChannel(groupDto.getIsChanel());
            group.setUsername(groupDto.getUsername());
            groupRepository.save(group);
            List<User> users = userRepository.findAllById(groupDto.getUsers());
            users.remove(from);
            for (User user : users) {
                GroupsAdminsPermissions adminsPermissions = new GroupsAdminsPermissions();
                Permission permission = permissionRepository.findByPermissionEnum(PermissionEnum.MEMBER).get();
                adminsPermissions.setPermission(permission);
                groupPermissionRepository.save(adminsPermissions);

                GroupsAdmins groupsAdmins = new GroupsAdmins();
                groupsAdmins.setGroup(group);
                groupsAdmins.setAdmin(user);
                groupsAdmins.setAdminsPermissions(Collections.singletonList(adminsPermissions));
                groupAdminRepository.save(groupsAdmins);

                adminsPermissions.setGroupsAdmins(groupsAdmins);
                groupPermissionRepository.save(adminsPermissions);
            }
            GroupsAdmins groupsAdmins = new GroupsAdmins();
            groupsAdmins.setAdmin(userRepository.findById(from).get());
            groupsAdmins.setGroup(group);
            groupAdminRepository.save(groupsAdmins);

            GroupsAdminsPermissions groupsAdminsPermissions = new GroupsAdminsPermissions();
            groupsAdminsPermissions.setPermission(permissionRepository.findByPermissionEnum(PermissionEnum.OWNER).get());
            groupsAdminsPermissions.setGroupsAdmins(groupsAdmins);
            groupPermissionRepository.save(groupsAdminsPermissions);

            group.setUsers(users);
            group.addUser(userRepository.findById(from).get());
            groupRepository.save(group);
            response.setSuccess(true);
            response.setMessage("successfully created");
            response.setData(group.getId());
        }catch (Exception e){
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public HttpEntity<?> sendMessage(GroupMessageDto messageDto, Long from) {
        ApiResponse response = new ApiResponse();
        try {
            if (!groupRepository.checkUser(from, messageDto.getGroupId())) throw new IllegalArgumentException("you have not joined the group. Please write after joining");

            Optional<Group> byId = groupRepository.findById(messageDto.getGroupId());
            if (!byId.isPresent()) throw new IllegalArgumentException("group not found");
            Group group = byId.get();
            GroupMessage message = new GroupMessage();
            message.setGroup(group);
            message.setFrom(userRepository.findById(from).get());
            message.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            message.setText(messageDto.getMessage());
            try {
                Optional<GroupMessage> byId1 = messageRepository.findById(messageDto.getReplyMessageId());
                if (!byId1.isPresent()) throw new NullPointerException("reply message not found");
                message.setReplayMsgId(messageDto.getReplyMessageId());
            }catch (Exception e){
            }
            message.setViewCount(1);
            messageRepository.save(message);
            response.setMessage("success");
            response.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> showAllGroups(Long userId) {
        ApiResponse apiResponse=new ApiResponse();
        List<Map<String, Object>> maps = groupRepository.showAllGroups(userId);
        apiResponse.setData(maps);
        apiResponse.setSuccess(true);
        if (maps.isEmpty()) {
            apiResponse.setMessage("You dont have any groups");
        }
        return ResponseEntity.ok(maps);
    }

    public HttpEntity<?> edit(GroupMessageDto groupMessageDto, Long userId) {
        ApiResponse response = new ApiResponse();
        try {
            GroupMessage groupMessage=messageRepository.getGroupMessageByUserId(userId);
            groupMessage.setText(groupMessageDto.getMessage());
            messageRepository.save(groupMessage);
            response.setSuccess(true);
            response.setMessage("Edited successfully");
        }catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
}
