package kr.co.frankit_assignment.api.user.application.command;

import kr.co.frankit_assignment.api.kernel.command.Command;
import kr.co.frankit_assignment.api.user.application.command.model.CreateUserCommandModel;
import kr.co.frankit_assignment.api.user.application.exception.DuplicateUserEmailException;
import kr.co.frankit_assignment.core.user.UserData;
import kr.co.frankit_assignment.core.user.UserFactory;
import kr.co.frankit_assignment.core.user.repository.write.UserWriteRepository;
import kr.co.frankit_assignment.core.user.vo.UserRoleType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserCommand implements Command<CreateUserCommandModel> {
    private final UserWriteRepository userWriteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void execute(CreateUserCommandModel model) {
        this.checkIfExists(model.getEmail());

        var user =
                new UserFactory(
                                UserData.builder()
                                        .id(model.getId())
                                        .email(model.getEmail())
                                        .password(passwordEncoder.encode(model.getPassword()))
                                        .roleType(UserRoleType.ROLE_USER)
                                        .build())
                        .create();

        userWriteRepository.save(user);
    }

    private void checkIfExists(@NonNull String email) {
        if (userWriteRepository.existsByEmail(email)) {
            throw new DuplicateUserEmailException("이미 가입된 이메일 입니다.");
        }
    }
}
