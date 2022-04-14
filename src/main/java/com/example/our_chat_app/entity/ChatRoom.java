package com.example.our_chat_app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "chat_rooms")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user1_id", "user2_id"}))

public class ChatRoom {

    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.ALL)
    private List<ChatRoomMessage> messages = new ArrayList<>();

    public void addMessage(ChatRoomMessage roomMessage) {
        messages.add(roomMessage);
    }

}
