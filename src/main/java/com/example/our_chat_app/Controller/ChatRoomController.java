package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.DelMessagesDto;
import com.example.our_chat_app.dto.MessageDto;
import com.example.our_chat_app.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
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
            @PathVariable Long to){
        Long userId= 1000009L;
        return chatRoomService.getMessages(to,userId, page,size);
    }


    @PutMapping("/edit")
    public HttpEntity<?> editMessage(@RequestBody MessageDto messageDto){
        return chatRoomService.editMessage(messageDto);
    }

    public static final Long fromId = 1000002L;

    @PostMapping("/send")
    public HttpEntity<?> sendMessageRoom(@Valid @RequestBody MessageDto messageDto) {
        Long userId= 1000002L;
        return chatRoomService.sendMessageRoom(userId,messageDto);
    }

    @GetMapping("/chats/{userId}")
    public HttpEntity<?> showAllChatsByUserId(@PathVariable Long userId){
        return chatRoomService.getAllUserChatsByUserId(userId);
    }



    @GetMapping("/unread")
    public HttpEntity<?> unreadMessage(){
        Long userId= 1000002L;
        return chatRoomService.getAllUnreadMessage(userId,true);
    }

    @GetMapping
    public HttpEntity<?> getAllChats(){
        Long userId= 1000002L;
        return chatRoomService.getAllUnreadMessage(userId,false);
    }

    @DeleteMapping("/messages")
    public HttpEntity<?> deleteMessagesByIds(@Valid @RequestBody DelMessagesDto messagesDto){
        Long userId= 1000007L;
        return chatRoomService.deleteMessages(messagesDto, userId);
    }


}
