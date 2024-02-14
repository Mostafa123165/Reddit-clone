package com.practice.reddit.controller;

import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.Subreddit;
import com.practice.reddit.entity.User;
import com.practice.reddit.mapper.PostMapper;
import com.practice.reddit.model.MessageStatusDto;
import com.practice.reddit.model.PostDto;
import com.practice.reddit.service.AuthService;
import com.practice.reddit.service.PostService;
import com.practice.reddit.service.SubredditService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService ;
    private final PostMapper postMapper ;
    private final AuthService authService ;
    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<MessageStatusDto> save(@Valid @RequestBody PostDto postDto) {

        User user = authService.getCurrentUser() ;
        Subreddit subreddit = subredditService.getSubreddit(postDto.getSubredditId());

        Post post = postService.save(postMapper.mapToPost(postDto,user,subreddit));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageStatusDto(
                        HttpStatus.CREATED.value()
                        ,"Create post successfully -" + post.getId() )) ;
    }

    @GetMapping(params = "id")
    public ResponseEntity<PostDto> getPost(@RequestParam Long id) {
        Post post = postService.getPost(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postMapper.mapToPostDto(post));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService
                        .getAllPosts()
                        .stream()
                        .map(postMapper::mapToPostDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("{subredditId}")
    public ResponseEntity<List<PostDto>> getPostBySubreddit(@PathVariable Long subredditId) {
        Subreddit subreddit = subredditService.getSubreddit(subredditId);

        List<PostDto> postsDto =  postService
                .getPostsBySubreddit(subreddit)
                .stream()
                .map(postMapper::mapToPostDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postsDto) ;
    }


    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> getPostsByUserId() {
        List<PostDto> postDto = postService.getPostsByUserId()
                .stream()
                .map(postMapper::mapToPostDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(postDto) ;
    }

    @PutMapping
    public ResponseEntity<MessageStatusDto> update(@Valid @RequestBody PostDto postDto) {

        postService.update(
                postDto
                ,authService.getCurrentUser()
                ,subredditService.getSubreddit(postDto.getSubredditId()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageStatusDto
                        .builder()
                        .status(HttpStatus.OK.value())
                        .message("Updated post successfully - " + postDto.getPostId() )
                        .build());
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<MessageStatusDto> delete(@RequestParam Long id) {

        postService.delete(id) ;

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageStatusDto
                        .builder()
                        .status(HttpStatus.OK.value())
                        .message("Deleted post successfully - " + id)
                        .build());
    }

}
