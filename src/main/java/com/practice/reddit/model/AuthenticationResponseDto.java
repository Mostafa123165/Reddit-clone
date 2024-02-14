package com.practice.reddit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String username;
    private String token ;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+2")
    private Instant expireAt;
}
