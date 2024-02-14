package com.practice.reddit.security;

import com.practice.reddit.model.CustomUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationTime;

    private Instant tokenExpiresAt, tokenIssuedAt;

    public String getToken(Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();

        return generateTokenWithUserName(user.getUsername(), user);
    }

    private String generateTokenWithUserName(String userName, CustomUser user) {
        tokenIssuedAt = Instant.now();
        tokenExpiresAt = tokenIssuedAt.plusSeconds(jwtExpirationTime);

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(tokenIssuedAt)
                .expiresAt(tokenExpiresAt)
                .subject(userName)
                .id(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("createdAt", convertFromInstantToString(user.getCreatedAt()))
                .claim("enabled" , user.getEnabled())
                .claim("authorities", user.getAuthorities())
                .issuer("self")
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    private String convertFromInstantToString(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("GMT+2"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return zonedDateTime.format(formatter) ;
    }

    public String validateToken(String token) {
        return this.jwtDecoder.decode(token).getSubject();
    }

}
