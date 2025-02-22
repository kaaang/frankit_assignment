package kr.co.frankit_assignment.core.user;

import kr.co.frankit_assignment.core.kernel.domain.AbstractDomainFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFactory implements AbstractDomainFactory<User> {
    private final UserData data;

    @Override
    public User create() {
        return User.builder()
                .id(data.getId())
                .email(data.getEmail())
                .password(data.getPassword())
                .roleType(data.getRoleType())
                .build();
    }
}
