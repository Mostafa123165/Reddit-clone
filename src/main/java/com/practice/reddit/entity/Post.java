package com.practice.reddit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="post_name")
    private String postName;

    @Column(name="url")
    private String url;

    @Column(name="description")
    @Lob
    private String description ;

    @Column(name="vote_count")
    private Integer voteCount ;

    @Column(name="created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subreddit_id")
    private Subreddit subreddit;

}
