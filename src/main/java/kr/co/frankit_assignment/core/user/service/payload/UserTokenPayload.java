package kr.co.frankit_assignment.core.user.service.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenPayload {
    private String accessToken;
    private int expiresIn;
}
