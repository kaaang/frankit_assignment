package kr.co.frankit_assignment.api.product.application.exception;

import kr.co.frankit_assignment.api.kernel.exception.AccessDeniedException;

public class ProductAccessDeniedException extends AccessDeniedException {
    public ProductAccessDeniedException(String message) {
        super(message);
    }
}
