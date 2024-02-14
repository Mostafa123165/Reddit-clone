package com.practice.reddit.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {

    private Long id ;

    private Long userId ;

    private String userName ;

    @NotNull(message = "postId is required")
    private Long postId ;

    @NotNull(message = "text is required")
    private String text ;

    private Instant createdAt ;
}
