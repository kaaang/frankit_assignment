package kr.co.frankit_assignment.core.user.service.payload;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTokenPayload {
    private String email;
    private String password;
}
