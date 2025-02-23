package kr.co.frankit_assignment.api.product.application.query.output;

import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.core.product.ProductOption;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionsOutput {
    private UUID id;
    private String name;
    private OptionType type;
    private List<String> values;
    private int extraPrice;

    public static ProductOptionsOutput of(ProductOption productOption) {
        return ProductOptionsOutput.builder()
                .id(productOption.getId())
                .name(productOption.getName())
                .type(productOption.getType())
                .values(productOption.getValues())
                .extraPrice(productOption.getExtraPrice())
                .build();
    }
}
