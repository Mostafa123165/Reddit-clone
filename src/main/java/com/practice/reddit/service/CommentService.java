package com.practice.reddit.service;

import com.practice.reddit.entity.Comment;
import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import com.practice.reddit.repository.CommentsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    final private CommentsRepository commentsRepository ;

    @Transactional
    public Long save(Comment comment) {
        commentsRepository.save(comment) ;
        return comment.getId() ;
    }

    public List<Comment> getCommentByPost(Post post) {
        return  commentsRepository.findByPost(post);
    }

    public List<Comment> getCommentByUser(User user) {
        return  commentsRepository.findByUser(user);
    }
}
