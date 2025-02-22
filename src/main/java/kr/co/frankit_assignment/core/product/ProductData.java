package kr.co.frankit_assignment.core.product;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductData {
    private UUID id;
    private String name;
    private String description;
    private int price;
    private int shippingFee;
    private UUID createdBy;
}
