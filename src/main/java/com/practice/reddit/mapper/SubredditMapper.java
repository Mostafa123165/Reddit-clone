package com.practice.reddit.mapper;

import com.practice.reddit.entity.Subreddit;
import com.practice.reddit.entity.User;
import com.practice.reddit.model.SubredditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "createdAt" , expression = "java(getInstant())")
    Subreddit mapToSubreddit(SubredditDto subredditDto);

    @Mapping(target = "userId" , expression = "java(mapUserId(subreddit.getUser()))")
    SubredditDto mapToSubredditDto(Subreddit subreddit);

    default Long mapUserId(User user) {

        return user.getId();
    }

    default Instant getInstant(){
        return Instant.now();
    }

}
