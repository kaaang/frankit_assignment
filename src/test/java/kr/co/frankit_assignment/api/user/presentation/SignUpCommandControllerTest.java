package kr.co.frankit_assignment.api.user.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import kr.co.frankit_assignment.api.user.presentation.request.UserCreateRequest;
import kr.co.frankit_assignment.config.TestBaseConfig;
import kr.co.frankit_assignment.config.payload.ResultActionsPayload;
import kr.co.frankit_assignment.core.user.UserData;
import kr.co.frankit_assignment.core.user.UserFactory;
import kr.co.frankit_assignment.core.user.repository.write.UserWriteRepository;
import kr.co.frankit_assignment.core.user.vo.UserRoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

class SignUpCommandControllerTest extends TestBaseConfig {
    @Autowired protected UserWriteRepository userWriteRepository;

    @Nested
    class CreatUserTest {
        private final String testEmail = "test@test.com";

        @BeforeEach
        void setUp() {
            var tempUser =
                    new UserFactory(
                                    UserData.builder()
                                            .id(UUID.randomUUID())
                                            .email(testEmail)
                                            .password("test")
                                            .roleType(UserRoleType.ROLE_USER)
                                            .build())
                            .create();

            userWriteRepository.save(tempUser);
        }

        @Test
        void shouldBeReturn400Error_WhenAlreadyEmail() throws Exception {
            var request = UserCreateRequest.builder().email(testEmail).password("test").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.POST)
                            .path("/users/signup")
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isBadRequest());
        }

        @Test
        void shouldBeReturn400Error_WhenEmailFormatInvalid() throws Exception {
            var request = UserCreateRequest.builder().email("test").password("test").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.POST)
                            .path("/users/signup")
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isBadRequest());
        }

        @Test
        void shouldBeReturn201() throws Exception {
            var request = UserCreateRequest.builder().email("kang@gmail.com").password("test").build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.POST)
                            .path("/users/signup")
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isCreated());
        }
    }
}
