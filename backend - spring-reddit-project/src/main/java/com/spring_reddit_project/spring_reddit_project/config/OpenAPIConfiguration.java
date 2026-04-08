package com.spring_reddit_project.spring_reddit_project.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    public OpenAPI redditAPI(){
        return new OpenAPI()
                .info(new Info().title("Reddit API")
                        .description("API for Reddit Application")
                        .version("v0.0.1")
                        .license(new License().name("Apache License Version 2.0").url("http://programmingtechie.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Expense Tracker Wiki Documentation")
                        .url("https://expensetracker.wiki/docs"));


    }
}
