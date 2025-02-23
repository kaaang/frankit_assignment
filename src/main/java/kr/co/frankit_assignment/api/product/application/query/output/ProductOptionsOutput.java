package kr.co.frankit_assignment.api.product.application.query.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.core.product.ProductOption;
import kr.co.frankit_assignment.core.product.ProductOptionValue;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionsOutput {
    private UUID id;
    private String name;
    private OptionType type;
    private int extraPrice;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> values;

    public static ProductOptionsOutput of(ProductOption productOption) {
        return ProductOptionsOutput.builder()
                .id(productOption.getId())
                .name(productOption.getName())
                .type(productOption.getType())
                .values(productOption.getValues().stream().map(ProductOptionValue::getValue).toList())
                .extraPrice(productOption.getExtraPrice())
                .build();
    }
}
