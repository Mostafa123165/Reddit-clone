package com.practice.reddit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subreddit")
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id ;

    @NotBlank(message = "Community name is required")
    @Column(name="name")
    private String name ;

    @NotBlank(message = "Description is required")
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "subreddit",
               fetch = FetchType.LAZY)
    private List<Post> posts ;

    @Column(name="created_at")
    private Instant createdAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
