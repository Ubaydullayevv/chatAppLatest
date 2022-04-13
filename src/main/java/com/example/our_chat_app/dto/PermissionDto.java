package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionDto {
     private Long groupId;
     private Long userId;
    private boolean giveDELETE_USER_ROLE;
    private boolean giveDELETEUSER_MESSAGE_ROLE;
    private boolean giveOWNER;
    private boolean giveCHANGE_AVATAR_ROLE;
    private boolean giveCHANGE_GROUPNAME_ROLE;




}
