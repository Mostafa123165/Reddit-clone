package com.practice.reddit.repository;

import com.practice.reddit.entity.Comment;
import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment,Long> {

     List<Comment> findByPost(Post post);

     List<Comment> findByUser(User user);
}
