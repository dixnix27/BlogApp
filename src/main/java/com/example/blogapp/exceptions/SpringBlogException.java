package com.example.blogapp.exceptions;

public class SpringBlogException extends RuntimeException {
    public SpringBlogException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringBlogException(String exMessage) {
        super(exMessage);
    }
}
