package kr.co.frankit_assignment.api.product.application.query.output;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.co.frankit_assignment.core.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class ProductOutput {
    @NonNull private UUID id;
    @NonNull private String name;
    @NonNull private String description;
    @NonNull private UUID createdBy;
    private int shippingFee;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductOutput from(Product product) {
        return ProductOutput.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .createdBy(product.getCreatedBy())
                .price(product.getPrice())
                .shippingFee(product.getShippingFee())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
