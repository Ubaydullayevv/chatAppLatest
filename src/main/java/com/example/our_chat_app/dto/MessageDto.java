package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    @NotNull(message = "please you don't select to users")
    private Long toId;
    @NotEmpty(message = "the message must not be empty")
    @NotBlank(message = "the message must consist of characters")
    @NotNull(message = "you must input your message")
    private String message;

}
