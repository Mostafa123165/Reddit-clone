package com.practice.reddit.mapper;

import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.Subreddit;
import com.practice.reddit.entity.User;
import com.practice.reddit.model.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Locale;

@Mapper(componentModel = "spring")
public interface PostMapper {


    @Mapping(target = "postId" , source = "id")
    @Mapping(target = "userId" , source = "user.id")
    @Mapping(target = "userName" , source = "user.userName")
    @Mapping(target = "subredditId" , source = "subreddit.id")
    @Mapping(target = "createdAt" , expression = "java(java.time.Instant.now())")
    @Mapping(target = "voteCount" , defaultValue = "0")
    @Mapping(target = "duration" , expression = "java(getDuration(post))")
    PostDto mapToPostDto(Post post) ;

    @Mapping(target = "id" , source = "postDto.postId")
    @Mapping(target = "user" , source = "user")
    @Mapping(target = "subreddit" , source = "subreddit")
    @Mapping(target = "postName" , source = "postDto.postName")
    @Mapping(target = "url" , source ="postDto.url")
    @Mapping(target = "description" ,source = "postDto.description")
    @Mapping(target = "createdAt" , expression = "java(java.time.Instant.now())")
    Post mapToPost(PostDto postDto , User user , Subreddit subreddit);

    default String getDuration(Post post) {
        PrettyTime p = new PrettyTime(new Locale("eg"));
        return p.format(post.getCreatedAt()) ;
    }
}