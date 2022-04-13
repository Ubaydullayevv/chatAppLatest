package com.example.our_chat_app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_permissions")
public class GroupsPermissions {

    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    @NotNull

    private Permission permission;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
