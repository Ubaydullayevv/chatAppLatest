package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.GroupDto;
import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.dto.PermissionDto;
import com.example.our_chat_app.entity.*;
import com.example.our_chat_app.entity.enums.PermissionEnum;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
            List<User> users = userRepository.findAllById(groupDto.getUsers());
            users.remove(from);
            group.setUsers(users);
            group.addUser(userRepository.findById(from).get());
            groupRepository.save(group);
            for (User user : users) {
                GroupsPermissions userPermissions = new GroupsPermissions();
                Permission permission = permissionRepository.findByPermissionEnum(PermissionEnum.MEMBER).get();
                userPermissions.setPermission(permission);
                userPermissions.setUser(user);
                userPermissions.setGroup(group);
                groupPermissionRepository.save(userPermissions);
            }
            GroupsPermissions groupsPermissions = new GroupsPermissions();
            groupsPermissions.setPermission(permissionRepository.findByPermissionEnum(PermissionEnum.OWNER).get());
            groupsPermissions.setUser(userRepository.findById(from).get());
            groupsPermissions.setGroup(group);
            groupPermissionRepository.save(groupsPermissions);
            groupRepository.save(group);
            response.setSuccess(true);
            response.setMessage("successfully created");
            response.setData(group.getId());
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public HttpEntity<?> sendMessage(GroupMessageDto messageDto, Long from) {
        ApiResponse response = new ApiResponse();
        try {
            if (!groupRepository.checkUser(from, messageDto.getGroupId()))
                throw new IllegalArgumentException("you have not joined the group. Please write after joining");
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            message.setViewCount(1);
            messageRepository.save(message);
            response.setMessage("success");
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> showAllGroups(Long userId) {
        ApiResponse apiResponse = new ApiResponse();
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
            GroupMessage groupMessage = messageRepository.getGroupMessageByUserId(userId);
            groupMessage.setText(groupMessageDto.getMessage());
            messageRepository.save(groupMessage);
            response.setSuccess(true);
            response.setMessage("Edited successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addMember(Long groupId, Long userId) {
        User user = userRepository.findById(userId).get();
        Optional<Group> byId = groupRepository.findById(groupId);
        Optional<Permission> byPermissionEnum = permissionRepository.findByPermissionEnum(PermissionEnum.MEMBER);
        groupPermissionRepository.save(new GroupsPermissions(null,byPermissionEnum.get(),user, byId.get()));
        byId.get().setUsers(Collections.singletonList(user));

        return ResponseEntity.ok(new ApiResponse("User Successfully added to this group"));
    }

    public ResponseEntity<ApiResponse> deleteMessage(Long messageId) {
        Optional<GroupMessage> byId = messageRepository.findById(messageId);
        messageRepository.delete(byId.get());
        return ResponseEntity.ok(new ApiResponse("Successfully deleted"));
    }

    public ResponseEntity<?> givePermission(PermissionDto permissionDto) {
        User user = userRepository.findById(permissionDto.getUserId()).get();
        Group byId = groupRepository.findById(permissionDto.getGroupId()).get();
        String msg="";
        if (permissionDto.isGiveCHANGE_AVATAR_ROLE()) {
            groupPermissionRepository.save(new GroupsPermissions(null,permissionRepository.findByPermissionEnum(PermissionEnum.CHANGE_AVATAR).get(),user, byId));
       msg+="Change avatar permission successfully added";
        }
        if (permissionDto.isGiveCHANGE_GROUPNAME_ROLE()) {
            groupPermissionRepository.save(new GroupsPermissions(null,permissionRepository.findByPermissionEnum(PermissionEnum.CHANGE_NAME).get(),user, byId));
            msg+="Change group name permission successfully added";
        }
        if (permissionDto.isGiveOWNER()) {
            groupPermissionRepository.save(new GroupsPermissions(null,permissionRepository.findByPermissionEnum(PermissionEnum.OWNER).get(),user, byId));
            byId.setCreatedBy(user);
            groupRepository.save(byId);
            msg+="Ownership transfered succesfully";
        }
        if (permissionDto.isGiveDELETE_USER_ROLE()) {
            groupPermissionRepository.save(new GroupsPermissions(null,permissionRepository.findByPermissionEnum(PermissionEnum.DELETE_USER).get(),user, byId));
            msg+="Delete user permission successfully added";
        }
        if (permissionDto.isGiveDELETEUSER_MESSAGE_ROLE()) {
            groupPermissionRepository.save(new GroupsPermissions(null,permissionRepository.findByPermissionEnum(PermissionEnum.DELETE_USER_MESSAGE).get(),user, byId));
            msg+="Delete user message permission successfully added";
        }
        return ResponseEntity.ok(msg);
    }
}
