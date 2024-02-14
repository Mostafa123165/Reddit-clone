package com.practice.reddit.controller;

import com.practice.reddit.entity.Comment;
import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import com.practice.reddit.mapper.CommentMapper;
import com.practice.reddit.model.CommentDto;
import com.practice.reddit.model.MessageStatusDto;
import com.practice.reddit.service.AuthService;
import com.practice.reddit.service.CommentService;
import com.practice.reddit.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentMapper commentMapper ;
    private final CommentService commentService ;
    private final AuthService authService ;
    private final PostService postService ;


    @PostMapping
    public ResponseEntity<MessageStatusDto> save(@Valid @RequestBody CommentDto commentDto) {
        User user =  authService.getCurrentUser() ;
        Post post =  postService.getPost(commentDto.getPostId());
        Comment comment = commentMapper.mapToComment(commentDto,user,post);
        Long id = commentService.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageStatusDto
                        .builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Saved comment successfully - " + id)
                        .build());
    }

    @GetMapping("/by/post/{id}")
    public ResponseEntity<List<CommentDto>> getCommentByPost(@PathVariable Long id) {
        Post post =postService.getPost(id);

        List<CommentDto> comments = commentService
                .getCommentByPost(post)
                .stream()
                .map(commentMapper::mapToCommentDto)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @GetMapping("/by/user")
    public ResponseEntity<List<CommentDto>> getCommentByUser() {
        User user = authService.getCurrentUser() ;

        List<CommentDto> comments = commentService
                .getCommentByUser(user)
                .stream()
                .map(commentMapper::mapToCommentDto)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

}
