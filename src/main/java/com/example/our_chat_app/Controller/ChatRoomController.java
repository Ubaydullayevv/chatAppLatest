package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.DelMessagesDto;
import com.example.our_chat_app.dto.MessageDto;
import com.example.our_chat_app.entity.User;
import com.example.our_chat_app.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    @Autowired
    ChatRoomService chatRoomService;

    @GetMapping("/{to}")
    public HttpEntity<?> getAllMessages(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @PathVariable Long to,  Authentication authentication){
        Long userId= ((User) authentication.getPrincipal()).getId();
        return chatRoomService.getMessages(to,userId, page,size);
    }


    @PutMapping("/edit")
    public HttpEntity<?> editMessage(@RequestBody MessageDto messageDto){
        return chatRoomService.editMessage(messageDto);
    }

    @PostMapping("/send")
    public HttpEntity<?> sendMessageRoom(@Valid @RequestBody MessageDto messageDto, Authentication authentication) {
        Long userId= ((User) authentication.getPrincipal()).getId();
        return chatRoomService.sendMessageRoom(userId,messageDto);
    }

    @GetMapping("/chats")
    public HttpEntity<?> showAllChatsByUserId(Authentication authentication){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return chatRoomService.getAllUserChatsByUserId(userId);
    }



    @GetMapping("/unread")
    public HttpEntity<?> unreadMessage(Authentication authentication){
        Long userId= ((User) authentication.getPrincipal()).getId();
        return chatRoomService.getAllUnreadMessage(userId,true);
    }

    @GetMapping
    public HttpEntity<?> getAllChats(Authentication authentication){
        Long userId= ((User) authentication.getPrincipal()).getId();
        return chatRoomService.getAllUnreadMessage(userId,false);
    }

    @DeleteMapping("/messages")
    public HttpEntity<?> deleteMessagesByIds(@Valid @RequestBody DelMessagesDto messagesDto, Authentication authentication){
        Long userId= ((User) authentication.getPrincipal()).getId();
        return chatRoomService.deleteMessages(messagesDto, userId);
    }


}
