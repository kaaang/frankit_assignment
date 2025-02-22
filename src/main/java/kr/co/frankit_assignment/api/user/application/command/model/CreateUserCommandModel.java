package kr.co.frankit_assignment.api.user.application.command.model;

import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserCommandModel implements CommandModel {
    private UUID id;
    private String email;
    private String password;
}
