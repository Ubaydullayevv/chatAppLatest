package com.example.our_chat_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false, unique = true)
    private String email;
    private String lastname;
    @Column(nullable = false, unique = true)
    private String username;
    @NotBlank
    @Column(nullable = false)
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private Attachment avatar;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank
    private String bio;
    private boolean isRegistrationCompleted;
    @Column(name = "last_seen")
    private Timestamp lastSeen = new Timestamp(System.currentTimeMillis());

    @OneToMany(mappedBy = "user1")
    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "user2")
    private List<ChatRoom> chatRooms1;

    @ManyToMany(mappedBy = "users")
//    @JoinTable(name = "groups_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    List<Group> groups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "from")
    List<ChatRoomMessage> chatRoomMessages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "from")
    List<GroupMessage> groupMessages = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        lastSeen = new Timestamp(System.currentTimeMillis());
        return new ArrayList<>();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
