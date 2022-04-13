package com.example.our_chat_app.service;

import com.example.our_chat_app.entity.User;
import com.example.our_chat_app.payload.ApiResponse;
import com.example.our_chat_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    public HttpEntity<?> getLikeUsername(String username) {
        ApiResponse response = new ApiResponse();
        try {
            List<Map<String, Object>> userByUsernameLike1 = userRepository.getUserByUsernameLike1(username);
            List<Map<String, Object>> maps = makeStatus(userByUsernameLike1);
            response.setSuccess(true);
            response.setMessage("success");
            response.setData(maps);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
    public List<Map<String, Object>> makeStatus(List<Map<String,Object>> users){
        List<Map<String, Object>> response = new ArrayList<>();
        for (Map<String, Object> user : users) {
            Timestamp leftAt = (Timestamp) user.get("lastSeen");
            Duration duration = Duration.between(leftAt.toLocalDateTime(),LocalDateTime.now());
            String status = getStatus(duration,leftAt.toLocalDateTime());
            Map<String, Object> map = new HashMap<>();
            Set<String> strings = user.keySet();
            strings.forEach(s -> map.put(s,user.get(s)));
            map.put("status", status);
            response.add(map);
        }
        return response;
    }

    public String getStatus(Duration l, LocalDateTime leftAt) {
        String status;
        if(l.toDays()==30) {
            return "a month ago";
        }
        if (l.toDays()>29) {
            status = leftAt.format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd"));
        }else {
            if(l.toHours()>47){
                status = l.toDays()+" days ago";
            } else {
                if(l.toHours()>23){
                    status = "a day ago";
                } else {
                    if(l.toHours()>1){
                        status = l.toHours()+" hours ago";
                    }else {
                        if(l.toHours()==1){
                            status = "a hour ago";
                        } else {
                            if (l.toMinutes()>1) {
                                status = l.toMinutes()+" minutes ago";
                            }else {
                                status = "a minute ago";
                            }
                        }
                    }
                }
            }
        }
        return status;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }else throw new UsernameNotFoundException("user not found");
    }
}
