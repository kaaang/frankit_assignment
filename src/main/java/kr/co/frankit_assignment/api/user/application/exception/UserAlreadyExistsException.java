package kr.co.frankit_assignment.api.user.application.exception;

import kr.co.frankit_assignment.api.kernel.exception.BadRequestException;

public class UserAlreadyExistsException extends BadRequestException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
