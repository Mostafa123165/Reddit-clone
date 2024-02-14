package com.practice.reddit.controller;

import com.practice.reddit.entity.Token;
import com.practice.reddit.model.AuthenticationResponseDto;
import com.practice.reddit.model.LoginDto;
import com.practice.reddit.model.RegisterDto;
import com.practice.reddit.security.JwtProvider;
import com.practice.reddit.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService ;

    @PostMapping("/signup")
    public Map<String,Object> signUp(@Valid @RequestBody RegisterDto registerBody) {

        Token token = authService.signUp(registerBody);
        return Collections.singletonMap("token",token.getToken()) ;
    }

    @GetMapping("/activateAccount/{token}")
    public Map<String,Object> activateAccount(@PathVariable String token) {

        authService.activateAccount(token);
        return Collections.singletonMap("message", "Account activated successfully") ;
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@Valid @RequestBody LoginDto loginDto) {

        return authService.login(loginDto);
    }

}
