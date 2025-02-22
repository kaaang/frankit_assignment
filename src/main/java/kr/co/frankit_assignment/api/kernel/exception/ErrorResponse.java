package kr.co.frankit_assignment.api.kernel.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @Builder.Default private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    @Builder.Default private List<String> errors = new ArrayList<>();
    @Builder.Default private String message = "Unknown";
    @Builder.Default private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
}
