package com.practice.reddit.service;

import com.practice.reddit.Exception.CustomNotFoundException;
import com.practice.reddit.entity.User;
import com.practice.reddit.model.CustomUser;
import com.practice.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUserName(username);
        User user = userOptional.orElseThrow(() -> new CustomNotFoundException("No user found with username - " + username));

        return new CustomUser
                (user.getId()
                        , user.getPassword()
                        , user.getUserName()
                        , user.getEnabled()
                        , true
                        , true
                        , true
                        , getAuthorities("ROLE_USER")
                        , user.getEmail()
                        , user.getCreatedAt());

    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

}
