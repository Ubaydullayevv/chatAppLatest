package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChannelDto {

    private String channelName;

    @Column(nullable = false, unique = true)
    private String channelUsername;

    private Boolean isPrivate;

    private Long avatarId;

    private Long ownerId;



}
