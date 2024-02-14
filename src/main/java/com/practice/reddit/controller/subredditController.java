package com.practice.reddit.controller;

import com.practice.reddit.mapper.SubredditMapper;
import com.practice.reddit.model.MessageStatusDto;
import com.practice.reddit.model.SubredditDto;
import com.practice.reddit.service.SubredditService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class subredditController {

    private final SubredditService subredditService;
    private final SubredditMapper subredditMapper ;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@Valid @RequestBody SubredditDto subredditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {

        SubredditDto subredditDto = subredditMapper
                .mapToSubredditDto(subredditService.getSubreddit(id)) ;

        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditDto) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageStatusDto> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.delete(id));
    }

}
