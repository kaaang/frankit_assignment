package kr.co.frankit_assignment.api.product.application.exception;

import kr.co.frankit_assignment.api.kernel.exception.NotFoundException;

public class ProductOptionNotFoundException extends NotFoundException {
    public ProductOptionNotFoundException(String message) {
        super(message);
    }
}
