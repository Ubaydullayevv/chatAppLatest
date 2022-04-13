package com.example.our_chat_app.Controller;


import com.example.our_chat_app.dto.UserDto;
import com.example.our_chat_app.security.JWTProvider;
import com.example.our_chat_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JWTProvider jwtProvider;


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid  @RequestBody UserDto userDto) {
        UserDetails userDetails = userService.loadUserByUsername(userDto.getUsername());
        String generatedToken = jwtProvider.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(generatedToken);


    }
    @GetMapping("home/{userId}")
    public  ResponseEntity<?> homePage(@PathVariable Long userId){
        return userService.getHomePage(userId);
    }

//    @PostMapping("/register")
//    public HttpEntity<?> register(@Valid @RequestBody UserRegisterDto userRegisterDto){
//        return userService.register(userRegisterDto);
//    }

}
