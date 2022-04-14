package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChannelDto {

    private String channelName;

    private String channelUsername;

    private Boolean isPrivate;

    private Long avatarId;

    List<Long> users = new ArrayList<>();



}
