package kr.co.frankit_assignment.api.user.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequest {
    @NotBlank(message = "빈 값을 입력할 수 없습니다.")
    @Email(message = "정상적이지 않은 이메일 입니다.")
    private String email;

    @NotBlank(message = "비밀번호를 빈 값일 수 없습니다.")
    private String password;
}
