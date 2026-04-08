    package com.spring_reddit_project.spring_reddit_project.dto;

    import com.spring_reddit_project.spring_reddit_project.model.VoteType;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class VoteDto {
        private VoteType voteType;
        private Long postId;
    }
