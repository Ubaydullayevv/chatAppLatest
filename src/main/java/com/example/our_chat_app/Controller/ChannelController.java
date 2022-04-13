package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.ChannelDto;

import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.dto.MessageDto;

import com.example.our_chat_app.entity.User;

import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.projection.ChannelProjection;
import com.example.our_chat_app.projection.PostProjection;
import com.example.our_chat_app.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    ChannelService channelService;


    @GetMapping
    public HttpEntity<?> getAllChannels() {
        Long userId=1000001L;
        List<?> allChannels = channelService.getAllChannels(userId);
        ApiResponse response = new ApiResponse("success", true, allChannels);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/posts/{channelId}")
    public HttpEntity<?> getAllPosts(@PathVariable Long channelId) {
        List<?> allPosts = channelService.getAllPosts(channelId);
        ApiResponse response = new ApiResponse("success", true, allPosts);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public HttpEntity<?> createChannel(@Valid @RequestBody ChannelDto channelDto, Authentication authentication) {
        Long userid = ((User) authentication.getPrincipal()).getId();
        return channelService.createChannel(userid,channelDto);
    }


    @PostMapping("/writePost")
    public HttpEntity<?> writePost(@Valid @RequestBody GroupMessageDto groupMessageDto) {
        Long userid = 1000009L;
        return channelService.writePost(userid, groupMessageDto);
    }


    // TODO: 4/13/2022 postni o'chirish uchun kanalni admini bo'lish kerak

    @DeleteMapping("/deletePost/{postId}")
    public HttpEntity<?> deletePost(@PathVariable Long postId) {
        return channelService.deletePost(postId);
    }












}
