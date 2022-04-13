package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.UserEditDto;
import com.example.our_chat_app.entity.User;
import com.example.our_chat_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/edit")
    public HttpEntity editUser(@RequestBody UserEditDto userEditDto, Authentication authentication){
        Long id = ((User) authentication.getPrincipal()).getId();
        return userService.edit(id, userEditDto);
    }
}
