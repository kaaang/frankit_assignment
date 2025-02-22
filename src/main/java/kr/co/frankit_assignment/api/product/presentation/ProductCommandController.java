package kr.co.frankit_assignment.api.product.presentation;

import jakarta.validation.Valid;
import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandExecutor;
import kr.co.frankit_assignment.api.kernel.presentation.response.HttpApiResponse;
import kr.co.frankit_assignment.api.product.application.command.CreateProductCommand;
import kr.co.frankit_assignment.api.product.application.command.DeleteProductCommand;
import kr.co.frankit_assignment.api.product.application.command.UpdateProductCommand;
import kr.co.frankit_assignment.api.product.application.command.model.CreateProductCommandModel;
import kr.co.frankit_assignment.api.product.application.command.model.DeleteProductCommandModel;
import kr.co.frankit_assignment.api.product.application.command.model.UpdateProductCommandModel;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductCreateRequest;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductUpdateRequest;
import kr.co.frankit_assignment.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCommandController {
    private final CreateProductCommand createProductCommand;
    private final UpdateProductCommand updateProductCommand;
    private final DeleteProductCommand deleteProductCommand;

    @PostMapping
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_USER_ROLE)")
    public ResponseEntity<Object> create(
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

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_USER_ROLE)")
    public ResponseEntity<Object> update(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ProductUpdateRequest request,
            @PathVariable UUID id) {
        new CommandExecutor<>(
                        updateProductCommand,
                        UpdateProductCommandModel.builder()
                                .id(id)
                                .name(request.getName())
                                .description(request.getDescription())
                                .price(request.getPrice())
                                .shippingFee(request.getShippingFee())
                                .userId(user.getId())
                                .build())
                .invoke();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_USER_ROLE)")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        new CommandExecutor<>(
                        deleteProductCommand,
                        DeleteProductCommandModel.builder().id(id).userId(user.getId()).build())
                .invoke();

        return ResponseEntity.ok().build();
    }
}
