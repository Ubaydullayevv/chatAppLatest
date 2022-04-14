package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.UserEditDto;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public List<Map<String, Object>> makeStatus(List<Map<String, Object>> users) {
        List<Map<String, Object>> response = new ArrayList<>();
        for (Map<String, Object> user : users) {
            Timestamp leftAt = (Timestamp) user.get("lastSeen");
            Duration duration = Duration.between(leftAt.toLocalDateTime(), LocalDateTime.now());
            String status = getStatus(duration, leftAt.toLocalDateTime());
            Map<String, Object> map = new HashMap<>();
            Set<String> strings = user.keySet();
            strings.forEach(s -> map.put(s, user.get(s)));
            map.put("status", status);
            response.add(map);
        }
        return response;
    }

    public String getStatus(Duration l, LocalDateTime leftAt) {
        String status;
        if (l.toDays() == 30) {
            return "a month ago";
        }
        if (l.toDays() > 29) {
            status = leftAt.format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd"));
        } else {
            if (l.toHours() > 47) {
                status = l.toDays() + " days ago";
            } else {
                if (l.toHours() > 23) {
                    status = "a day ago";
                } else {
                    if (l.toHours() > 1) {
                        status = l.toHours() + " hours ago";
                    } else {
                        if (l.toHours() == 1) {
                            status = "a hour ago";
                        } else {
                            if (l.toMinutes() > 1) {
                                status = l.toMinutes() + " minutes ago";
                            } else {
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
        } else throw new UsernameNotFoundException("user not found");
    }

    public ResponseEntity<?> getHomePage(Long userId) {
        String groups = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/group/showAllGroups/")
                .toUriString();
        String chats = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/chat/chats/")
                .toUriString();
        String unreadMessage = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/chat/unread/")
                .toUriString();
        String allMessage = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/chat")
                .toUriString();
        String profile = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user/edit")
                .toUriString();
        Map<String, String> maps = new HashMap<>();
        maps.put("Groups", groups);
        maps.put("Chats", chats);
        maps.put("Unread Messages", unreadMessage);
        maps.put("All messages", allMessage);
        maps.put("Profile", profile);
        return ResponseEntity.ok(maps);

    }

    public HttpEntity edit(Long id, UserEditDto userEditDto) {
        ApiResponse response = new ApiResponse();
        try {
            Optional<User> byIdUser = userRepository.findById(id);
            User user = byIdUser.get();
            user.setBio(userEditDto.getBio());
            user.setEmail(userEditDto.getEmail());
            user.setFirstname(userEditDto.getFirstname());
            user.setLastname(userEditDto.getLastname());
            user.setUsername(userEditDto.getUsername());
            user.setPassword(userEditDto.getPassword());
            user.setPhoneNumber(userEditDto.getPhoneNumber());
            userRepository.save(user);
            response.setMessage("Edited successfully");
            response.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public boolean getAuthority(String username,Long groupId, String ...permission) {
        List<String> authority = userRepository.getAuthority(username, groupId);
        for (String s : authority) {
            for (String s1 : permission) {
                if (s.equals(s1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean noAuthority(String username,Long groupId, String ...permission ) {
        List<String> authority = userRepository.getAuthority(username, groupId);
        boolean check = false;
        for (String s : authority) {
            for (String s1 : permission) {
                check = s1.equals(s);
                if (check) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkDeletePost(String username, Long postId, String ...permissions) {
        List<String> authorityByPostId = userRepository.getAuthorityByPostId(postId, username);
        for (String s : authorityByPostId) {
            for (String permission : permissions) {
                if (s.equals(permission)) {
                    return true;
                }
            }
        }
        return false;
    }
}
