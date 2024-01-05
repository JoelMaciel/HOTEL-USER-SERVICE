package com.joelmaciel.userservice.domain.exceptions;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(UUID userId) {
        this(String.format("User with given uuid not found %s", userId));
    }
}
