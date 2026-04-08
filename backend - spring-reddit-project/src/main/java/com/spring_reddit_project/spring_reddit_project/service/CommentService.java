package com.spring_reddit_project.spring_reddit_project.service;

import com.spring_reddit_project.spring_reddit_project.dto.CommentsDto;
import com.spring_reddit_project.spring_reddit_project.exceptions.PostNotFoundException;
import com.spring_reddit_project.spring_reddit_project.mapper.CommentMapper;
import com.spring_reddit_project.spring_reddit_project.model.Comment;
import com.spring_reddit_project.spring_reddit_project.model.NotificationEmail;
import com.spring_reddit_project.spring_reddit_project.model.Post;
import com.spring_reddit_project.spring_reddit_project.model.User;
import com.spring_reddit_project.spring_reddit_project.repository.CommentRepository;
import com.spring_reddit_project.spring_reddit_project.repository.PostRepository;
import com.spring_reddit_project.spring_reddit_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private final static String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;


    public void save(CommentsDto commentsDto){
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));

        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }
}
