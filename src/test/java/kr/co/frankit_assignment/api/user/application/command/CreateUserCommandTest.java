package kr.co.frankit_assignment.api.user.application.command;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.UUID;
import kr.co.frankit_assignment.api.user.application.command.model.CreateUserCommandModel;
import kr.co.frankit_assignment.api.user.application.exception.UserAlreadyExistsException;
import kr.co.frankit_assignment.config.UnitTestConfig;
import kr.co.frankit_assignment.core.user.repository.write.UserWriteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

class CreateUserCommandTest extends UnitTestConfig {
    @InjectMocks private CreateUserCommand command;

    @Mock private CreateUserCommandModel model;
    @Mock private UserWriteRepository userWriteRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @Test
    void shouldBeThrowUserAlreadyExistsException_WhenRepositoryReturnTrue() {
        given(model.getEmail()).willReturn("test");
        given(userWriteRepository.existsByEmail(anyString())).willReturn(true);

        assertThatThrownBy(() -> command.execute(model)).isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void shouldBeCallRepositorySave() {
        given(model.getId()).willReturn(UUID.randomUUID());
        given(model.getEmail()).willReturn("test");
        given(model.getPassword()).willReturn("password");

        command.execute(model);

        then(userWriteRepository).should(times(1)).save(any());
    }
}
