package kr.co.frankit_assignment.api.kernel.presentation.vo;

import java.util.List;
import kr.co.frankit_assignment.core.user.vo.UserRoleType;
import org.springframework.stereotype.Component;

@Component("RoleContainer")
public class RoleContainer {
    public static final List<String> ALLOW_USER_ROLE = List.of(UserRoleType.ROLE_USER.name());
}
