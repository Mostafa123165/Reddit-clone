package com.practice.reddit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type")
    private VoteType voteType ;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;
}
