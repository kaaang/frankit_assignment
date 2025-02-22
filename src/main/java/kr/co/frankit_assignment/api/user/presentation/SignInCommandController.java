package kr.co.frankit_assignment.api.user.presentation;

import jakarta.validation.Valid;
import kr.co.frankit_assignment.api.user.application.service.UserTokenService;
import kr.co.frankit_assignment.api.user.presentation.request.UserSignInRequest;
import kr.co.frankit_assignment.api.user.presentation.response.UserTokenResponse;
import kr.co.frankit_assignment.core.user.service.payload.CreateTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignInCommandController {
    private final UserTokenService userTokenService;

    @PostMapping(value = "/signin")
    public ResponseEntity<Object> signIn(@Valid @RequestBody UserSignInRequest request) {
        var token =
                userTokenService.createToken(
                        CreateTokenPayload.builder()
                                .email(request.getEmail())
                                .password(request.getPassword())
                                .build());

        return ResponseEntity.ok(
                UserTokenResponse.builder()
                        .accessToken(token.getAccessToken())
                        .expiresIn(token.getExpiresIn())
                        .build());
    }
}
