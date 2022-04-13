package com.example.our_chat_app.Controller;

import com.example.our_chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public HttpEntity getUserByUsername(@PathVariable String username){
        return userService.getLikeUsername(username);
    }

}
