package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.ChannelDto;
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
        List<ChannelProjection> allChannels = channelService.getAllChannels();
        ApiResponse response = new ApiResponse("success", true, allChannels);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/posts")
    public HttpEntity<?> getAllPosts() {
        List<PostProjection> allPosts = channelService.getAllPosts();
        ApiResponse response = new ApiResponse("success", true, allPosts);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public HttpEntity<?> createChannel(@Valid @RequestBody ChannelDto channelDto, Authentication authentication) {
        Long userid = ((User) authentication.getPrincipal()).getId();
        return channelService.createChannel(userid,channelDto);
    }










}
