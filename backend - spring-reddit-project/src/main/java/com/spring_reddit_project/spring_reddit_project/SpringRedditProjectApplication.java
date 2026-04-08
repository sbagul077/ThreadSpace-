package com.spring_reddit_project.spring_reddit_project;

import com.spring_reddit_project.spring_reddit_project.config.OpenAPIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(OpenAPIConfiguration.class)
public class SpringRedditProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedditProjectApplication.class, args);
	}

}
