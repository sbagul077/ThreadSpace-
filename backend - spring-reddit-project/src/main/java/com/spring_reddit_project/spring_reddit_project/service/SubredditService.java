package com.spring_reddit_project.spring_reddit_project.service;


import com.spring_reddit_project.spring_reddit_project.dto.SubredditDto;
import com.spring_reddit_project.spring_reddit_project.exceptions.SpringRedditException;
import com.spring_reddit_project.spring_reddit_project.mapper.SubredditMapper;
import com.spring_reddit_project.spring_reddit_project.model.Subreddit;
import com.spring_reddit_project.spring_reddit_project.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final AuthService authService;   // ✅ add this
//    @Transactional
//    public SubredditDto save(SubredditDto subredditDto){
//        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
//        subredditDto.setId(save.getId());
//        return subredditDto;
//    }

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit subreddit = subredditMapper.mapDtoToSubreddit(subredditDto);

        subreddit.setCreatedDate(Instant.now());              // ✅ set created date
        subreddit.setUser(authService.getCurrentUser());      // ✅ set logged-in user

        Subreddit saved = subredditRepository.save(subreddit);
        return subredditMapper.mapSubredditToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
       return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id){
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit with " + id + " was found"));

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
