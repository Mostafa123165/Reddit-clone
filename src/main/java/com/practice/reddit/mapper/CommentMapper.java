package com.practice.reddit.mapper;

import com.practice.reddit.entity.Comment;
import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import com.practice.reddit.model.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "createdAt" , expression = "java(java.time.Instant.now())")
    @Mapping(target = "post" , source = "post")
    @Mapping(target = "user" , source = "user")
    Comment mapToComment(CommentDto commentDto , User user , Post post);

    @Mapping(target = "userId" , source = "comment.user.id")
    @Mapping(target = "postId" , source = "comment.post.id")
    @Mapping(target = "userName" , source = "comment.user.userName")
    CommentDto mapToCommentDto(Comment comment);
}
