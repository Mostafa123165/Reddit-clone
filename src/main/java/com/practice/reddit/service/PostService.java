package com.practice.reddit.service;

import com.practice.reddit.Exception.CustomNotFoundException;
import com.practice.reddit.entity.Post;
import com.practice.reddit.entity.Subreddit;
import com.practice.reddit.entity.User;
import com.practice.reddit.mapper.PostMapper;
import com.practice.reddit.model.PostDto;
import com.practice.reddit.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PostService {


    final private PostRepository postRepository ;
    final private AuthService authService ;
    final private PostMapper postMapper ;
    @Transactional
    public List<Post> getPostsBySubreddit(Subreddit subreddit) {
        return postRepository.findBySubreddit(subreddit) ;
    }


    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    @Transactional
    public List<Post> getPostsByUserId() {
        User user = authService.getCurrentUser() ;
        return postRepository.findByUser(user) ;
    }

    @Transactional
    public Post getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        post.orElseThrow(() -> new CustomNotFoundException("Not found post with id - " + id ));

       return post.get();
    }

    @Transactional
    public void update(PostDto postDto, User user , Subreddit subreddit) {

        Post oldPost = getPost(postDto.getPostId()) ;

        // check subreddit and user don't chang ;
        if(!Objects.equals(user.getId(), oldPost.getUser().getId()) ||
                !Objects.equals(oldPost.getSubreddit().getId() , subreddit.getId())) {
            throw new RuntimeException("Owner | subreddit of post is not correct.") ;
        }

        postRepository.save(postMapper.mapToPost(postDto , user , subreddit)) ;
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
