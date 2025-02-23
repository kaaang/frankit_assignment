package kr.co.frankit_assignment.api.product.presentation.rqeuest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionUpdateRequest {
    @NotBlank(message = "옵션명은 필수값 입니다.")
    private String name;

    @NotNull(message = "옵션 타입은 필수값 입니다.")
    private OptionType type;

    @Builder.Default private List<String> values = new ArrayList<>();

    private int extraPrice;
}
