package kr.co.frankit_assignment.api.kernel.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
