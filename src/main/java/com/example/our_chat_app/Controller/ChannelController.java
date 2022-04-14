package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.GroupDto;
import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.entity.User;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.service.ChannelService;
import com.example.our_chat_app.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @Autowired
    GroupService groupService;


    @GetMapping
    public HttpEntity<?> getAllChannels(Authentication authentication) {
        Long userId=((User) authentication.getPrincipal()).getId();
        List<?> allChannels = channelService.getAllChannels(userId);
        ApiResponse response = new ApiResponse("success", true, allChannels);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/posts/{channelId}")
    public HttpEntity<?> getAllPosts(@PathVariable Long channelId,
                                     @RequestParam(required = false, defaultValue = "5") int size,
                                     @RequestParam(required = false, defaultValue = "1") int page
                                     ) {
        Page<?> allPosts = channelService.getAllPosts(channelId, page - 1, size);
        ApiResponse response = new ApiResponse("success", true, allPosts);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public HttpEntity<?> createChannel(@Valid @RequestBody GroupDto channelDto, Authentication authentication) {
        Long userid = ((User) authentication.getPrincipal()).getId();
        return groupService.createGroup(channelDto, null, userid);
    }



    @PostMapping("/writePost")
    @PreAuthorize("@userService.getAuthority(principal.username, #groupMessageDto.groupId,'OWNER') and @userService.noAuthority(principal.username,#groupMessageDto.groupId,'BLOCKED')")
    public HttpEntity<?> writePost(@Valid @RequestBody GroupMessageDto groupMessageDto, Authentication authentication) {
        Long userid = ((User) authentication.getPrincipal()).getId();
        return channelService.writePost(userid,groupMessageDto);
    }


    // TODO: 4/13/2022 postni o'chirish uchun kanalni admini bo'lish kerak

    @DeleteMapping("/deletePost/{postId}")
    @PreAuthorize("@userService.checkDeletePost(principal.username, #postId,'OWNER')")
    public HttpEntity<?> deletePost(@PathVariable Long postId) {
        return channelService.deletePost(postId);
    }

    @PostMapping("/addMember")
    @PreAuthorize("@userService.getAuthority(principal.username, #groupId ,'OWNER')")
    public ResponseEntity<?> addMember(
            @RequestParam Long groupId,
            @RequestParam Long userId) {
        return groupService.addMember(groupId,userId);
    }













}
