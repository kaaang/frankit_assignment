package kr.co.frankit_assignment.api.product.application.query.field;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ProductsField {
    @NonNull private Integer size;
    private LocalDateTime lastViewedAt;
    private UUID lastViewedId;
}
