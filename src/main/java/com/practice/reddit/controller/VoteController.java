package com.practice.reddit.controller;

import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import com.practice.reddit.entity.Vote;
import com.practice.reddit.mapper.VoteMapper;
import com.practice.reddit.model.MessageStatusDto;
import com.practice.reddit.model.VoteDto;
import com.practice.reddit.service.AuthService;
import com.practice.reddit.service.PostService;
import com.practice.reddit.service.VoteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    final private VoteService voteService ;
    final private VoteMapper voteMapper ;
    final private PostService postService ;
    final private AuthService authService ;

    @PostMapping
    public ResponseEntity<?> vote(@Valid @RequestBody VoteDto voteDto) {

        User user = authService.getCurrentUser() ;
        Post post = postService.getPost(voteDto.getPostId());
        Vote vote = voteMapper.mapToVote(voteDto,post,user);
        voteService.vote(vote) ;

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @DeleteMapping(params = "postId")
    public ResponseEntity<?> delete(@RequestParam Long postId) {
        User user = authService.getCurrentUser() ;
        Post post = postService.getPost(postId) ;
        voteService.delete(user,post) ;
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
