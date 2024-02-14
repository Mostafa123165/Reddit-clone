package com.practice.reddit.service;

import com.practice.reddit.Exception.CustomNotFoundException;
import com.practice.reddit.entity.Token;
import com.practice.reddit.entity.User;
import com.practice.reddit.mapper.User_CustomUserMapper;
import com.practice.reddit.model.*;
import com.practice.reddit.repository.TokenRepository;
import com.practice.reddit.repository.UserRepository;
import com.practice.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository ;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder ;
    private final MailService mailService ;
    private final AuthenticationManager authenticationManager ;
    private final JwtProvider jwtProvider ;
    private final User_CustomUserMapper userCustomUserMapper ;

    public Token signUp(RegisterDto registerBody) {

        User user = new User() ;
        user.setUserName(registerBody.getUserName());
        user.setEmail(registerBody.getEmail());
        user.setPassword(passwordEncoder.encode(registerBody.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setEnabled(false);

        userRepository.save(user) ;
        Token token = generateToken(user) ;
        mailService.sendMail(new NotificationEmailDto(
                "Please Activate your account",
                user.getEmail(),
                "Thank you for signing up to Reddit, " +
                      "please click on the below url to activate your account : " +
                       "http://localhost:8080/api/auth/activateAccount/" + token.getToken()
                ));
        return token ;

    }

    private Token generateToken(User user) {
       String token =  UUID.randomUUID().toString() ;
       Token theToken = new Token();
       theToken.setToken(token);
       theToken.setUser(user);
       tokenRepository.save(theToken) ;
       return theToken ;
    }

    public void activateAccount(String token) {
        Optional<Token> theToken =  tokenRepository.findByToken(token) ;
        theToken.orElseThrow(() -> new CustomNotFoundException("Invalid token"));
        fetchUserAndEnable(theToken.get()) ;
    }

    public void fetchUserAndEnable(Token theToken) {
        User theUser = theToken.getUser();
        theUser.setEnabled(true);
        userRepository.save(theUser);
    }

    public AuthenticationResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername()
                ,loginDto.getPassword()));


        String token = jwtProvider.getToken(authentication) ;

        Optional<User> user = userRepository.findByUserName(loginDto.getUsername()) ;
        user.orElseThrow(()-> new CustomNotFoundException("Not found user with userName - " + loginDto.getUsername()));



        return AuthenticationResponseDto.builder()
                .token(token)
                .username(loginDto.getUsername())
                .expireAt(jwtProvider.getTokenExpiresAt()).build();
    }

    public User getCurrentUser() {

        CustomUser customUser = (CustomUser)SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();

        return userCustomUserMapper.mapToUser(customUser);
    }



}
