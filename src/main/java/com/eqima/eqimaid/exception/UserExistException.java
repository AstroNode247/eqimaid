package com.eqima.eqimaid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
