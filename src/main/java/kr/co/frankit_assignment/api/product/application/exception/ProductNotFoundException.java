package kr.co.frankit_assignment.api.product.application.exception;

import kr.co.frankit_assignment.api.kernel.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
