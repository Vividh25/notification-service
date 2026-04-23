package com.vividh.notificationservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId) {
        super("User not found wit id : " + userId);
    }
}
