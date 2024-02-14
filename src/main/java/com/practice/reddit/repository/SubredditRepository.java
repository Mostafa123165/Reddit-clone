package com.practice.reddit.repository;

import com.practice.reddit.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
}
