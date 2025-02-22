package kr.co.frankit_assignment.api.user.application.exception;

import kr.co.frankit_assignment.api.kernel.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
