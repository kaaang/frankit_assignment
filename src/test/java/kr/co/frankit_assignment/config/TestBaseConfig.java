package kr.co.frankit_assignment.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;
import kr.co.frankit_assignment.config.payload.ResultActionsPayload;
import kr.co.frankit_assignment.core.user.User;
import kr.co.frankit_assignment.core.user.UserData;
import kr.co.frankit_assignment.core.user.UserFactory;
import kr.co.frankit_assignment.core.user.vo.UserRoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Disabled
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({
    TestPostgresContainerConfig.class,
})
@ActiveProfiles(value = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public abstract class TestBaseConfig {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    protected MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    protected User test_one_user;
    protected User test_two_user;

    @BeforeEach
    void setUp() {
        params.clear();

        this.test_one_user =
                new UserFactory(
                                UserData.builder()
                                        .id(UUID.randomUUID())
                                        .email("user1@gmail.com")
                                        .password("test")
                                        .roleType(UserRoleType.ROLE_USER)
                                        .build())
                        .create();

        this.test_two_user =
                new UserFactory(
                                UserData.builder()
                                        .id(UUID.randomUUID())
                                        .email("user2@gmail.com")
                                        .password("test")
                                        .roleType(UserRoleType.ROLE_USER)
                                        .build())
                        .create();
    }

    protected <T, V> ResultActions getResultActions(@Nonnull ResultActionsPayload<T, V> payload)
            throws Exception {
        var requestBuilder = payload.getRequestBuilder();

        if (Objects.nonNull(payload.getRequest())) {
            requestBuilder
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(payload.getRequest()));
        }

        if (Objects.nonNull(payload.getUserDetails())) {
            requestBuilder.with(user(payload.getUserDetails()));
        }

        if (!params.isEmpty()) {
            requestBuilder.params(params);
        }

        return mockMvc.perform(requestBuilder).andDo(print());
    }
}
