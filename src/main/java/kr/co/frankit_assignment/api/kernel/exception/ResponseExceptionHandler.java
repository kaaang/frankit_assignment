package kr.co.frankit_assignment.api.kernel.exception;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(
            NotFoundException e, ServletWebRequest request) {
        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(
            BadRequestException e, ServletWebRequest request) {
        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> conflictException(ConflictException e, ServletWebRequest request) {
        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.CONFLICT)
                        .errors(List.of(e.getMessage()))
                        .path(request.getRequest().getRequestURI())
                        .build();

        return this.toResponseEntity(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        var errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                        .toList();

        var errorResponse =
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(errors)
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    private ResponseEntity<Object> toResponseEntity(ErrorResponse response) {
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
