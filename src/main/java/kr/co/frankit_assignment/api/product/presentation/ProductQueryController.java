package kr.co.frankit_assignment.api.product.presentation;

import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.presentation.response.HttpApiResponse;
import kr.co.frankit_assignment.api.product.application.query.ProductQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductQueryController {
    private final ProductQuery productQuery;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(@RoleContainer.ALLOW_USER_ROLE)")
    public ResponseEntity<Object> detail(@PathVariable UUID id) {
        var output = productQuery.getOr404ById(id);

        return ResponseEntity.ok(HttpApiResponse.of(output));
    }
}
