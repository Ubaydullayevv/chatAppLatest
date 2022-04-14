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
    public HttpEntity<?> createGroup(@Valid @RequestPart GroupDto groupDto, @RequestPart(required = false) MultipartFile avatar, Authentication authentication) {
        Long from = ((User) authentication.getPrincipal()).getId();
        return groupService.createGroup(groupDto, avatar, from);
    }

    @PostMapping("/send")
    @PreAuthorize("@userService.noAuthority(principal.username,#messageDto.groupId,'BLOCKED')")
    public HttpEntity<?> sendMessage(@Valid @RequestBody GroupMessageDto messageDto,  Authentication authentication) {
        Long from = ((User) authentication.getPrincipal()).getId();
        return groupService.sendMessage(messageDto, from);
    }

    @PutMapping("/edit")
    public HttpEntity<?> editGroupMessage(@RequestBody GroupMessageDto groupMessageDto, Authentication authentication){
        Long id = ((User) authentication.getPrincipal()).getId();
        return groupService.edit(groupMessageDto,id);
    }
    @GetMapping("/showAllGroups")
    public ResponseEntity<?> showAllGroups(Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return groupService.showAllGroups(userId);
    }
//    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping("/showAllMessages/{groupId}")
    public ResponseEntity<?> showAllMessages(@PathVariable Long groupId, @RequestParam(required = false, defaultValue = "5") int size,
                                             @RequestParam(required = false, defaultValue = "1") int page) {
        return groupService.showAllMessages(groupId,page-1,size);
    }

    @PostMapping("/addMember")
    @PreAuthorize("@userService.getAuthority(principal.username, #groupId ,'OWNER')")
    public ResponseEntity<?> addMember(
            @RequestParam Long groupId,
            @RequestParam Long userId) {
        return groupService.addMember(groupId,userId);
    }
    @DeleteMapping("delete/{messageId}")
        public ResponseEntity<?> deleteMessage(@PathVariable Long messageId,Authentication authentication){
        Long userId = ((User) authentication.getPrincipal()).getId();

        return   groupService.deleteMessage(messageId,userId);
    }
    @PostMapping("/givePermission")
    @PreAuthorize("@userService.getAuthority(principal.username, #permissionDto.groupId, 'OWNER')")
    public  ResponseEntity<?> givePermission(@RequestBody PermissionDto permissionDto){
        return groupService.givePermission(permissionDto);
    }
}
