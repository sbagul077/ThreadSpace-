package com.spring_reddit_project.spring_reddit_project.service;


import com.spring_reddit_project.spring_reddit_project.dto.PostRequest;
import com.spring_reddit_project.spring_reddit_project.dto.PostResponse;
import com.spring_reddit_project.spring_reddit_project.exceptions.PostNotFoundException;
import com.spring_reddit_project.spring_reddit_project.exceptions.SubRedditNotFoundException;
import com.spring_reddit_project.spring_reddit_project.mapper.PostMapper;
import com.spring_reddit_project.spring_reddit_project.model.Post;
import com.spring_reddit_project.spring_reddit_project.model.Subreddit;
import com.spring_reddit_project.spring_reddit_project.model.User;
import com.spring_reddit_project.spring_reddit_project.repository.PostRepository;
import com.spring_reddit_project.spring_reddit_project.repository.SubredditRepository;
import com.spring_reddit_project.spring_reddit_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        User currentUser = authService.getCurrentUser();
        log.info("Current user is: {}", currentUser);
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubRedditNotFoundException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubRedditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}