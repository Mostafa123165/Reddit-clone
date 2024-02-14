package com.practice.reddit.model;


import com.practice.reddit.entity.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteDto {

    @NotNull(message = "voteType is required")
    private VoteType voteType ;

    @NotNull(message = "postId is required")
    private Long postId ;
}
