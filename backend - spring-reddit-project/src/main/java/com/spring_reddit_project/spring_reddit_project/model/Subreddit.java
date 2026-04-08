package com.spring_reddit_project.spring_reddit_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Subreddit {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    @Column(name = "created_date")
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
