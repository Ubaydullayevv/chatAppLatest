package com.example.our_chat_app.entity;

import com.example.our_chat_app.entity.enums.PermissionEnum;
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
@Table(name = "permissions")
public class Permission {

    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PermissionEnum permissionEnum;

}
