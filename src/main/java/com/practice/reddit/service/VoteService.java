package com.practice.reddit.service;

import com.practice.reddit.Exception.CustomNotFoundException;
import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.User;
import com.practice.reddit.entity.Vote;
import com.practice.reddit.entity.VoteType;
import com.practice.reddit.repository.PostRepository;
import com.practice.reddit.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    final private VoteRepository voteRepository;
    final private PostRepository postRepository;

    @Transactional
    public void vote(Vote vote) {

        // need to check if user make vote on this post or no .
        Optional<Vote> oldVote = voteRepository.findByUserAndPost(vote.getUser() , vote.getPost()) ;
        if(oldVote.isPresent() && oldVote.get().getVoteType().equals(vote.getVoteType())) {
            throw new RuntimeException("you have already " + vote.getVoteType() + " for this post");
        }

        // need add vote to post

        Post post =vote.getPost() ;

        updatePostAfterAddOrRemoveVote(post , vote.getVoteType() , false );

        oldVote.ifPresent(value -> vote.setId(value.getId()));
        voteRepository.save(vote) ;
    }

    @Transactional
    public void delete(User user , Post post) {

        Vote vote = getVote(user,post);

        updatePostAfterAddOrRemoveVote(post,vote.getVoteType(),true) ;
        voteRepository.delete(vote);
    }

    private Vote getVote(User user , Post post) {
        Optional<Vote> vote = voteRepository.findByUserAndPost(user,post) ;
        vote.orElseThrow(() -> new CustomNotFoundException("Not found vote with postId - " + post.getId() + " and userId - " + user.getId()));
        return  vote.get() ;
    }

    @Async
    public void updatePostAfterAddOrRemoveVote(Post post , VoteType voteType , boolean fromDelete) {

        int voteCount = post.getVoteCount()==null ? 0 : post.getVoteCount();

        if((voteType.equals(VoteType.UPVOTE) && fromDelete) || (voteType.equals(VoteType.DOWNVOTE) && !fromDelete)) {
            post.setVoteCount(voteCount-1);
        }
        else {
            post.setVoteCount(voteCount+1);
        }

        postRepository.save(post) ;
    }

}
