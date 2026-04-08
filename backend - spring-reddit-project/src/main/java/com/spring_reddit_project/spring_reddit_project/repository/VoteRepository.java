package com.spring_reddit_project.spring_reddit_project.repository;

import com.spring_reddit_project.spring_reddit_project.model.Post;
import com.spring_reddit_project.spring_reddit_project.model.User;
import com.spring_reddit_project.spring_reddit_project.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
