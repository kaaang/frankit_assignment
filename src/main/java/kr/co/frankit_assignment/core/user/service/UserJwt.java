package kr.co.frankit_assignment.core.user.service;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJwt {
    private String accessToken;
    private int expiresIn;
}
