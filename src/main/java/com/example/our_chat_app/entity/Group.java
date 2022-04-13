package com.example.our_chat_app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @NotNull
    @ManyToOne
    private User createdBy;
    @OneToOne(cascade = CascadeType.ALL)
    private Attachment groupAvatar;
    @ManyToMany
    @NotEmpty
    @JoinTable(name = "groups_users", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    @NotBlank
    private String bio;

    private boolean isChannel;
    @Column(unique = true,nullable = false)
    private String username;

    private boolean isPrivate;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GroupMessage> groupMessages = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }
}
