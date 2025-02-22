package kr.co.frankit_assignment.api.product.presentation.rqeuest;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "상품명은 필수값 입니다.")
    private String name;

    private String description;
    private int price;
    private int shippingFee;
}
