package com.example.our_chat_app.dto;

import com.example.our_chat_app.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEditDto {

    String firstname;
    String email;
    String lastname;
    String username;
    String password;
    String phoneNumber;
    String bio;

}
