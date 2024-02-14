package com.practice.reddit.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubredditDto {

    private Long id ;

    private Long userId ;

    @NotBlank(message = "Community name is required")
    private String name ;

    @NotBlank(message = "description is required")
    private String  description ;

    private Long numberOfPosts;

}
