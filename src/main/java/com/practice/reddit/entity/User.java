package com.practice.reddit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "userName is required")
    @Column(name="user_name")
    private String userName;

    @NotBlank(message = "password is required")
    @Column(name="password")
    private String password;

    @Email
    @NotBlank(message = "email is required")
    @Column(name="email")
    private String email ;

    @Column(name="created_at")
    private Instant createdAt;

    @Column(name="enabled")
    private Boolean enabled;

}
