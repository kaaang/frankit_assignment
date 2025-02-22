package kr.co.frankit_assignment.core.user.service.exception;

import io.jsonwebtoken.JwtException;

public class UserJwtException extends RuntimeException {
    public UserJwtException(String message, JwtException cause) {
        super(message, cause);
    }
}
