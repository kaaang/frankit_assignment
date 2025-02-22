package kr.co.frankit_assignment.api.product.presentation.rqeuest;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NonNull
@AllArgsConstructor
public class ProductsRequest {
    @Builder.Default private Integer size = 10;
    private UUID lastViewedId;
    private LocalDateTime lastViewedAt;
}
