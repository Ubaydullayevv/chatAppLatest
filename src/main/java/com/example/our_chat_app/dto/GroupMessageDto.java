package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupMessageDto {
    @NotNull(message = "you must select a group")
    Long groupId ;
    @NotEmpty(message = "the message must not be empty")
    @NotBlank(message = "the message must consist of characters")
    @NotNull(message = "you must input your message")
    String message;
    Long replyMessageId;
}
