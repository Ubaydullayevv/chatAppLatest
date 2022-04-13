package com.example.our_chat_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "chat_messages")
public class ChatRoomMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_read")
    private boolean isRead = false;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column(nullable = false, columnDefinition = "text")
    private String text;
    @NotNull
    private Timestamp createdAt=new Timestamp(System.currentTimeMillis());

    @NotNull
    @ManyToOne
    private ChatRoom chatRoom;
    @NotNull
    @ManyToOne
    private User from;

    Boolean deleteFrom = false;

    private Timestamp updatedAt;


}
