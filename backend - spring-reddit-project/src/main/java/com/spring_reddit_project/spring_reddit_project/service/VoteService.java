package com.spring_reddit_project.spring_reddit_project.service;

import com.spring_reddit_project.spring_reddit_project.dto.VoteDto;
import com.spring_reddit_project.spring_reddit_project.exceptions.PostNotFoundException;
import com.spring_reddit_project.spring_reddit_project.exceptions.SpringRedditException;
import com.spring_reddit_project.spring_reddit_project.model.Post;
import com.spring_reddit_project.spring_reddit_project.model.Vote;
import com.spring_reddit_project.spring_reddit_project.model.VoteType;
import com.spring_reddit_project.spring_reddit_project.repository.PostRepository;
import com.spring_reddit_project.spring_reddit_project.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.spring_reddit_project.spring_reddit_project.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());


        if(voteByPostAndUser.isPresent() &&
            voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'D for this post");
        }

        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else{
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
