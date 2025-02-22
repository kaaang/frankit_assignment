package kr.co.frankit_assignment.core.product;

import kr.co.frankit_assignment.core.kernel.domain.AbstractDomainFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductFactory implements AbstractDomainFactory<Product> {
    private final ProductData data;

    @Override
    public Product create() {
        return Product.builder()
                .id(data.getId())
                .name(data.getName())
                .description(data.getDescription())
                .price(data.getPrice())
                .shippingFee(data.getShippingFee())
                .createdBy(data.getCreatedBy())
                .build();
    }
}
