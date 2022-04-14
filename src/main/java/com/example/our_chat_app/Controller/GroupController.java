package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.GroupDto;
import com.example.our_chat_app.dto.GroupMessageDto;
import com.example.our_chat_app.dto.PermissionDto;
import com.example.our_chat_app.entity.User;
import com.example.our_chat_app.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping(consumes = {"multipart/form-data"})
    public HttpEntity<?> createGroup(@Valid @RequestPart GroupDto groupDto, @RequestPart(required = false) MultipartFile avatar) {
        Long from = 1000009L;
        return groupService.createGroup(groupDto, avatar, from);
    }

    @PostMapping("/send")
    public HttpEntity<?> sendMessage(@Valid @RequestBody GroupMessageDto messageDto) {
        Long from = 1000009L;
        return groupService.sendMessage(messageDto, from);
    }

    @PutMapping("/edit")
//    @PreAuthorize("")
    public HttpEntity<?> editGroupMessage(@RequestBody GroupMessageDto groupMessageDto, Authentication authentication){
        Long id = ((User) authentication.getPrincipal()).getId();
        return groupService.edit(groupMessageDto,id);
    }
    @GetMapping("/showAllGroups")
    public ResponseEntity<?> showAllGroups() {
        Long userId = 1000009L;
        return groupService.showAllGroups(userId);
    }
    @GetMapping("/showAllMessages/{groupId}")
    public ResponseEntity<?> showAllMessages(@PathVariable Long groupId) {
        return groupService.showAllMessages(groupId);
    }

    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(
            @RequestParam Long groupId,
            @RequestParam Long userId) {
        return groupService.addMember(groupId,userId);
    }
    @GetMapping("delete/{messageId}")
        public ResponseEntity<?> deleteMessage(@PathVariable Long messageId){
      return   groupService.deleteMessage(messageId);
    }
    @PostMapping("/givePermission")
    public  ResponseEntity<?> givePermission(@RequestBody PermissionDto permissionDto){
        return groupService.givePermission(permissionDto);
    }
}
