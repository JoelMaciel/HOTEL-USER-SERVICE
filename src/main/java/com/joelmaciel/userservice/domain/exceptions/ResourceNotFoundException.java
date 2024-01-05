package com.joelmaciel.userservice.domain.exceptions;

public abstract class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
