package com.practice.reddit.mapper;

import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import com.practice.reddit.entity.Vote;
import com.practice.reddit.model.VoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "voteType" , source = "voteDto.voteType")
    @Mapping(target = "post" , source = "post")
    @Mapping(target = "user" , source = "user")
    Vote mapToVote(VoteDto voteDto, Post post , User user) ;

}
