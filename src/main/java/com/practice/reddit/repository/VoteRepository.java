package com.practice.reddit.repository;

import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import com.practice.reddit.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {

    Optional<Vote> findByUserAndPost(User user , Post post) ;
    void deleteByUserAndPost(User user , Post post) ;
}
