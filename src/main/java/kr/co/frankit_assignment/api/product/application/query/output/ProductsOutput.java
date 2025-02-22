package kr.co.frankit_assignment.api.product.application.query.output;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.co.frankit_assignment.core.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class ProductsOutput {
    @NonNull private UUID id;
    @NonNull private String name;
    @NonNull private UUID createdBy;
    private int shippingFee;
    private int price;
    private LocalDateTime createdAt;

    public static ProductsOutput from(Product product) {
        return ProductsOutput.builder()
                .id(product.getId())
                .name(product.getName())
                .createdBy(product.getCreatedBy())
                .price(product.getPrice())
                .shippingFee(product.getShippingFee())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
