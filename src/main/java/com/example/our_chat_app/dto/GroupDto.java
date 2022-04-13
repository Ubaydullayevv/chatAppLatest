package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupDto {
    @NotEmpty(message = "the  username must not be empty")
    @NotBlank(message = "the username must consist of characters")
    @NotNull(message = "you must input your username")
    String username;

    @NotEmpty(message = "the name must not be empty")
    @NotBlank(message = "the name must consist of characters")
    @NotNull(message = "you must input your name")
    String name;
    Boolean isPrivate =false ;
    Boolean isChanel = false;
    String bio;
    @NotEmpty(message = "you cannot create a group for only yourself")
    List<Long> users = new ArrayList<>();
}
