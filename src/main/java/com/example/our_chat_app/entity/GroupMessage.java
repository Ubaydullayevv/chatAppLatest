package com.example.our_chat_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "group_message")

public class GroupMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String text;
    private int viewCount = 1;
    private Timestamp createdAt=new Timestamp(System.currentTimeMillis());

    @ManyToOne
    private Group group;

    @NotNull
    @ManyToOne
    private User from;

    private Timestamp updatedAt;

    private Long replayMsgId;









}
