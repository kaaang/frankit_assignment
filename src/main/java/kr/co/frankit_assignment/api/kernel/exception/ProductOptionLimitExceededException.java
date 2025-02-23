package kr.co.frankit_assignment.api.kernel.exception;

public class ProductOptionLimitExceededException extends BadRequestException {
    public ProductOptionLimitExceededException(String message) {
        super(message);
    }
}
