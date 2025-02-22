package kr.co.frankit_assignment.api.user.application.service;

import kr.co.frankit_assignment.api.user.application.exception.UserNotFoundException;
import kr.co.frankit_assignment.core.user.User;
import kr.co.frankit_assignment.core.user.repository.read.UserReadRepository;
import kr.co.frankit_assignment.core.user.service.JwtTokenProvider;
import kr.co.frankit_assignment.core.user.service.payload.CreateTokenPayload;
import kr.co.frankit_assignment.core.user.service.payload.UserTokenPayload;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenService {
    private final UserReadRepository userReadRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserTokenPayload createToken(CreateTokenPayload payload) {
        var user = this.getUserOr404ByEmail(payload.getEmail());
        this.validatePassword(payload.getPassword(), user.getPassword());

        return this.createToken(user);
    }

    private User getUserOr404ByEmail(@NonNull String email) {
        return userReadRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private void validatePassword(String raw, String to) {
        if (!passwordEncoder.matches(raw, to)) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    private UserTokenPayload createToken(@NonNull User user) {
        var jwt = jwtTokenProvider.generateToken(user.getId(), user);

        return UserTokenPayload.builder()
                .accessToken(jwt.getAccessToken())
                .expiresIn(jwt.getExpiresIn())
                .build();
    }
}
