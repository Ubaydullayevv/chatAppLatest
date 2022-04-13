package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.GroupDto;
import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping(consumes = {"multipart/form-data"})
    public HttpEntity<?> createGroup(@Valid @RequestPart GroupDto groupDto, @RequestPart(required = false) MultipartFile avatar){
        Long from= 1000009L;
        return groupService.createGroup(groupDto, avatar, from);
    }

    @PostMapping("/send")
    public HttpEntity<?> sendMessage(@Valid @RequestBody GroupMessageDto messageDto) {
        Long from= 1000009L;
        return groupService.sendMessage(messageDto, from);
    }
    @GetMapping("/showAllGroups/{userId}")
    public ResponseEntity<?> showAllGroups(@PathVariable Long userId){
        return groupService.showAllGroups(userId);
}


}
