package com.spring_reddit_project.spring_reddit_project.mapper;

import com.spring_reddit_project.spring_reddit_project.dto.SubredditDto;
import com.spring_reddit_project.spring_reddit_project.model.Post;
import com.spring_reddit_project.spring_reddit_project.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target =  "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

//    default Integer mapPosts(List<Post> numberOfPosts){
//        return numberOfPosts.size();
//    }

    default Integer mapPosts(List<Post> posts){
        return posts != null ? posts.size() : 0;
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
