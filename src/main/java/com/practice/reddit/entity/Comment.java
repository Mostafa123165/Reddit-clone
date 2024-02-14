package com.practice.reddit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private Instant createdAt ;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;


}
