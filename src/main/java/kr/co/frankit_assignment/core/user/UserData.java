package kr.co.frankit_assignment.core.user;

import java.util.UUID;
import kr.co.frankit_assignment.core.user.vo.UserRoleType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserData {
    private UUID id;
    private String email;
    private String password;
    private UserRoleType roleType;
}
