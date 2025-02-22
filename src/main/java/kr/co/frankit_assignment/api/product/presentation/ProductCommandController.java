package kr.co.frankit_assignment.api.product.presentation;

import jakarta.validation.Valid;
import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandExecutor;
import kr.co.frankit_assignment.api.kernel.presentation.response.HttpApiResponse;
import kr.co.frankit_assignment.api.product.application.command.CreateProductCommand;
import kr.co.frankit_assignment.api.product.application.command.model.CreateProductCommandModel;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductCreateRequest;
import kr.co.frankit_assignment.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCommandController {
    private final CreateProductCommand createProductCommand;

    @PostMapping
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_USER_ROLE)")
    public ResponseEntity<Object> signIn(
            @AuthenticationPrincipal User user, @Valid @RequestBody ProductCreateRequest request) {
        var id = UUID.randomUUID();
        new CommandExecutor<>(
                        createProductCommand,
                        CreateProductCommandModel.builder()
                                .id(id)
                                .name(request.getName())
                                .description(request.getDescription())
                                .price(request.getPrice())
                                .shippingFee(request.getShippingFee())
                                .createdBy(user.getId())
                                .build())
                .invoke();

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpApiResponse.of(id));
    }
}
