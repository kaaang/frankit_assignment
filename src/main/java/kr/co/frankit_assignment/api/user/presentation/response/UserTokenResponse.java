package kr.co.frankit_assignment.api.user.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenResponse {
    private String accessToken;
    private int expiresIn;
}
