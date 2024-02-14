package com.practice.reddit.service;

import com.practice.reddit.Exception.CustomNotFoundException;
import com.practice.reddit.entity.Subreddit;
import com.practice.reddit.entity.User;
import com.practice.reddit.mapper.SubredditMapper;
import com.practice.reddit.mapper.User_CustomUserMapper;
import com.practice.reddit.model.CustomUser;
import com.practice.reddit.model.MessageStatusDto;
import com.practice.reddit.model.SubredditDto;
import com.practice.reddit.repository.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository ;
    private final SubredditMapper subredditMapper ;
    private final User_CustomUserMapper userCustomUserMapper ;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {

        Subreddit subreddit = subredditMapper.mapToSubreddit(subredditDto) ;
        subreddit.setUser(getUserFromToken());

        subredditRepository.save(subreddit);
        return subredditMapper.mapToSubredditDto(subreddit);
    }

    @Transactional
    public List<SubredditDto> getAll() {

        return subredditRepository.findAll().stream().map(
                subredditMapper::mapToSubredditDto
        ).collect(Collectors.toList());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Subreddit getSubreddit(Long id) {

        Optional<Subreddit> subreddit=  subredditRepository.findById(id) ;
        subreddit.orElseThrow(() -> new CustomNotFoundException("No subreddit found with id - " + id));

        return subreddit.get() ;
    }

    private User getUserFromToken() {

        CustomUser customUser = (CustomUser)SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();

        return userCustomUserMapper.mapToUser(customUser);
    }

    @Transactional
    public MessageStatusDto delete(Long id) {
        getSubreddit(id) ;
        subredditRepository.deleteById(id);
        return MessageStatusDto.builder()
                .status(HttpStatus.OK.value())
                .message("Deleted subreddit Successfully id -" + id).build();
    }

}
