package com.example.our_chat_app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "admins", uniqueConstraints = {@UniqueConstraint(columnNames = {"admin_id", "group_id"})})
public class GroupsAdmins {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @NotEmpty
    @OneToMany(mappedBy = "groupsAdmins")
    List<GroupsAdminsPermissions> adminsPermissions = new ArrayList<>();

    public GroupsAdmins(User admin, Group group) {
        this.admin = admin;
        this.group = group;
    }
}
