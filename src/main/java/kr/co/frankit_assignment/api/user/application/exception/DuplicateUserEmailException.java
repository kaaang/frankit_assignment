package kr.co.frankit_assignment.api.user.application.exception;

import kr.co.frankit_assignment.api.kernel.exception.ConflictException;

public class DuplicateUserEmailException extends ConflictException {
    public DuplicateUserEmailException(String message) {
        super(message);
    }
}
