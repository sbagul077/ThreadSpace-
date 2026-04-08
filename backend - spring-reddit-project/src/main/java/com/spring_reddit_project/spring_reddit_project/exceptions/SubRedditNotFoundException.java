package com.spring_reddit_project.spring_reddit_project.exceptions;

import org.aspectj.bridge.IMessage;

public class SubRedditNotFoundException extends RuntimeException {
    public SubRedditNotFoundException(String message) {
        super(message);
    }
}
