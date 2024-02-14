package com.practice.reddit.model;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long postId ;

    private Long userId ;

    private String userName ;

    @NotNull    (message = "subredditId is required")
    private Long subredditId;

    @NotBlank(message = "postName is required")
    private String postName ;

    @Lob
    private String description ;
    private Instant createdAt ;
    private String url ;

    // new fields
    private int voteCount;

    private String duration;
}
