package kr.co.frankit_assignment.api.product.presentation;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandExecutor;
import kr.co.frankit_assignment.api.kernel.presentation.response.HttpApiResponse;
import kr.co.frankit_assignment.api.product.application.command.*;
import kr.co.frankit_assignment.api.product.application.command.model.*;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductCreateRequest;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductOptionRequest;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductUpdateRequest;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import kr.co.frankit_assignment.core.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCommandController {
    private final CreateProductCommand createProductCommand;
    private final UpdateProductCommand updateProductCommand;
    private final DeleteProductCommand deleteProductCommand;

    private final CreateProductOptionCommand createProductOptionCommand;
    private final UpdateProductOptionCommand updateProductOptionCommand;

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

    @PostMapping("/{id}/options")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_USER_ROLE)")
    public ResponseEntity<Object> createOption(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @Valid @RequestBody ProductOptionRequest request)
            throws BindException {
        this.validateOption(request.getType(), request.getValues());
        new CommandExecutor<>(
                        createProductOptionCommand,
                        CreateProductOptionCommandModel.builder()
                                .productId(id)
                                .userId(user.getId())
                                .name(request.getName())
                                .type(request.getType())
                                .extraPrice(request.getExtraPrice())
                                .values(request.getValues())
                                .build())
                .invoke();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}/options/{optionId}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_USER_ROLE)")
    public ResponseEntity<Object> updateOption(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @PathVariable UUID optionId,
            @Valid @RequestBody ProductOptionRequest request)
            throws BindException {
        this.validateOption(request.getType(), request.getValues());
        new CommandExecutor<>(
                        updateProductOptionCommand,
                        UpdateProductOptionCommandModel.builder()
                                .productId(id)
                                .userId(user.getId())
                                .optionId(optionId)
                                .name(request.getName())
                                .type(request.getType())
                                .extraPrice(request.getExtraPrice())
                                .values(request.getValues())
                                .build())
                .invoke();

        return ResponseEntity.ok().build();
    }

    private void validateOption(@NonNull OptionType type, List<String> values) throws BindException {
        if (type.equals(OptionType.SELECT) && CollectionUtils.isEmpty(values)) {
            var bindException = new BindException(this, "productOption");
            bindException.addError(
                    new FieldError(
                            "productOption",
                            "values", // 필드명
                            "SELECT 타입은 최소 1개 이상의 값을 가져야 합니다."));

            throw bindException;
        }

        if (type.equals(OptionType.TEXT) && !CollectionUtils.isEmpty(values)) {
            var bindException = new BindException(this, "productOption");
            bindException.addError(
                    new FieldError(
                            "productOption",
                            "values", // 필드명
                            "TEXT 타입은 values를 지정할 수 없습니다."));

            throw bindException;
        }
    }
}
