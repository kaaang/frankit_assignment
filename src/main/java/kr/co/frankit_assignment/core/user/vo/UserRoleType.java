package kr.co.frankit_assignment.core.user.vo;

import java.util.Arrays;
import lombok.NonNull;

public enum UserRoleType {
    ROLE_UNAUTHORIZED,
    ROLE_USER;

    public static UserRoleType findBy(@NonNull String type) {
        return Arrays.stream(UserRoleType.values())
                .filter(item -> item.name().equals(type))
                .findFirst()
                .orElse(ROLE_UNAUTHORIZED);
    }
}
