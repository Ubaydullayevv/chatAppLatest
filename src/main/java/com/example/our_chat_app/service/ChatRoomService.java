package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.DelMessagesDto;
import com.example.our_chat_app.dto.MessageDto;
import com.example.our_chat_app.entity.ChatRoom;
import com.example.our_chat_app.entity.ChatRoomMessage;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.repository.ChatMessagesRepository;
import com.example.our_chat_app.repository.ChatRoomRepository;
import com.example.our_chat_app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
@Transactional
@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatMessagesRepository chatMessagesRepository;

    @Autowired
    UserRepository userRepository;


    public HttpEntity<?> getMessages(Long to, Long userId, int page, int size) {
        ApiResponse response = new ApiResponse();
        try {
            Map<String, Object> allMessagesPage = chatRoomRepository.findAllMessagesPage(to, userId, size, page);
            Map<String, Object> map = stringToJsonArray(allMessagesPage, "messages");
            response.setMessage("success");
            response.setData(map);
            response.setSuccess(true);
            chatRoomRepository.checkMessageRead(to, userId, page, size);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }


    public HttpEntity<?> sendMessageRoom(Long fromId, MessageDto messageDto) {
        ApiResponse response = new ApiResponse();
        try {
            ChatRoom chatRoom = null;
            Optional<ChatRoom> byUser1IdAndUser2Id = chatRoomRepository.findByUser1IdAndUser2Id(fromId, messageDto.getToId());
            if (!byUser1IdAndUser2Id.isPresent()) {
                byUser1IdAndUser2Id = chatRoomRepository.findByUser1IdAndUser2Id(messageDto.getToId(), fromId);
                if (!byUser1IdAndUser2Id.isPresent()) {
                    chatRoom = new ChatRoom();
                    chatRoom.setUser1(userRepository.findById(fromId).get());
                    chatRoom.setUser2(userRepository.findById(messageDto.getToId()).get());
                    chatRoomRepository.save(chatRoom);
                } else chatRoom = byUser1IdAndUser2Id.get();
            } else chatRoom = byUser1IdAndUser2Id.get();
            ChatRoomMessage roomMessage = new ChatRoomMessage();
            roomMessage.setChatRoom(chatRoom);
            roomMessage.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            roomMessage.setText(messageDto.getMessage());
            roomMessage.setFrom(userRepository.findById(fromId).get());
            chatMessagesRepository.save(roomMessage);
            chatRoom.addMessage(roomMessage);
            chatRoomRepository.save(chatRoom);
            response.setMessage("successfully sent");
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);

    }


    /*
            List map to json array method
     */
    public List<Map<String, Object>> stringToJsonArrayList(List<Map<String, Object>> list, String jsonColumnName) {
//        return list.stream().map(stringMap -> stringMap.put(jsonColumnName, new JSONObject("{ \"" + jsonColumnName + "\" : " + stringMap.get(jsonColumnName)).getJSONArray(jsonColumnName))).collect(Collectors.toList());
        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : list) {
            Map<String, Object> map = stringToJsonArray(stringObjectMap, jsonColumnName);
            if (map.size() > 0) data.add(map);
        }
        return data;
//        JSONArray rows = new JSONObject("{ \"rows\" :"+stringObjectMap.get("rows").toString()+"}").getJSONArray("rows");
    }


    /*
    map to json array
     */
    public Map<String, Object> stringToJsonArray(Map<String, Object> stringObjectMap, String jsonColumnName) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        try {
            if (stringObjectMap.get(jsonColumnName) != null) {
                map.put(jsonColumnName, mapper.readValue(stringObjectMap.get(jsonColumnName).toString(), ArrayList.class));
            }
            List<String> collect = new ArrayList<>(stringObjectMap.keySet());
            collect.remove(jsonColumnName);
            for (String s : collect) map.put(s, stringObjectMap.get(s));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }


    public HttpEntity<?> getAllUnreadMessage(Long from, boolean b) {
        ApiResponse response = new ApiResponse();
        try {
            List<Map<String, Object>> allUnreadMsg = new ArrayList<>();
            if (b) allUnreadMsg = chatRoomRepository.getAllUnreadMsg(from);
            else allUnreadMsg = chatRoomRepository.getAllChats(from);
            response.setMessage("success");
            response.setData(allUnreadMsg);
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public HttpEntity<?> getAllUserChatsByUserId(Long from) {
        ApiResponse response = new ApiResponse();
        try {
            List<Map<String, Object>> allUserChats = new ArrayList<>();
            /*if (b) */
            allUserChats = chatRoomRepository.getAllUserChatsById(from);
            /*else allUserChats = chatRoomRepository.getAllChats(from);*/
            response.setMessage("success");
            response.setData(allUserChats);
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }


    public HttpEntity<?> deleteMessages(DelMessagesDto messagesDto, Long from) {
        ApiResponse response = new ApiResponse();
        List<Long> notDeleted = new ArrayList<>();
        try {
            if (messagesDto.getDeleteForEveryone()) {
                for (Long id : messagesDto.getIds()) {
                    try {
                        if (!chatMessagesRepository.checkUserCanDelete(from, id))
                            throw new IllegalArgumentException("you can not delete this message");
                        chatMessagesRepository.deleteById(id);
                    } catch (Exception e) {
                        notDeleted.add(id);
                        System.err.println(e.getMessage());
                    }
                }
            } else {
                for (Long id : messagesDto.getIds()) {
                    try {
                        if (!chatMessagesRepository.checkUserCanDelete(from, id))
                            throw new IllegalArgumentException("you can not delete this message");
                        if (chatMessagesRepository.checkDeletion(id, from)) {
                            System.out.println("message id: " + id);
                            System.out.println("from id: " + from);
                            chatMessagesRepository.deleteById(id);
                        } else {
                            Optional<ChatRoomMessage> byId = chatMessagesRepository.findById(id);
                            if (!byId.isPresent()) throw new NullPointerException("id topilmadi");
                            ChatRoomMessage chatRoomMessage = byId.get();
                            chatRoomMessage.setDeleteFrom(chatRoomMessage.getFrom().getId().equals(from));
                            System.out.println("message id: " + id);
                            System.out.println("from id: " + from);
                            chatMessagesRepository.save(chatRoomMessage);

                        }
                    } catch (Exception e) {
                        notDeleted.add(id);
                        System.err.println(e.getMessage());
                    }
                }
            }
            if (notDeleted.size() > 0) {
                response.setMessage("some message deleted");
            } else response.setMessage("successfully deleted");
            response.setData(notDeleted);
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public HttpEntity<?> editMessage(MessageDto messageDto) {
        ApiResponse response = new ApiResponse();
        try {
            ChatRoomMessage byIdMessage = chatMessagesRepository.getById(messageDto.getId());
            byIdMessage.setText(messageDto.getMessage());
            chatMessagesRepository.save(byIdMessage);
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
