package kr.co.frankit_assignment.api.product.application.command.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandModel;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class UpdateProductOptionCommandModel implements CommandModel {
    @NonNull private UUID productId;
    @NonNull private UUID userId;
    @NonNull private UUID optionId;
    @NonNull private String name;
    @NonNull private OptionType type;
    private int extraPrice;

    @Builder.Default private List<String> values = new ArrayList<>();
}
