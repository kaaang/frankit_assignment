package kr.co.frankit_assignment.api.kernel.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> notFoundException(NotFoundException e, ServletWebRequest request) {
        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> badRequestException(
            BadRequestException e, ServletWebRequest request) {
        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> toResponseEntity(ErrorResponse response) {
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
