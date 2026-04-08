package com.spring_reddit_project.spring_reddit_project.exceptions;

public class SpringRedditException extends RuntimeException{
    public SpringRedditException(String exMessage, Exception exception){
        super(exMessage, exception);
    }

    public SpringRedditException(String exMessage){
        super(exMessage);
    }
}
