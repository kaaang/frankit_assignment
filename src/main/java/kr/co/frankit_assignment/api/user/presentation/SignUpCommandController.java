package kr.co.frankit_assignment.api.user.presentation;

import jakarta.validation.Valid;
import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandExecutor;
import kr.co.frankit_assignment.api.user.application.command.CreateUserCommand;
import kr.co.frankit_assignment.api.user.application.command.model.CreateUserCommandModel;
import kr.co.frankit_assignment.api.user.presentation.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignUpCommandController {
    private final CreateUserCommand createUserCommand;

    @PostMapping(value = "/signup")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest request) {
        new CommandExecutor<>(
                        createUserCommand,
                        CreateUserCommandModel.builder()
                                .id(UUID.randomUUID())
                                .email(request.getEmail())
                                .password(request.getPassword())
                                .build())
                .invoke();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
