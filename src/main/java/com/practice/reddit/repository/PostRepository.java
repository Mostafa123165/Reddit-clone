package com.practice.reddit.repository;

import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.Subreddit;
import com.practice.reddit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
