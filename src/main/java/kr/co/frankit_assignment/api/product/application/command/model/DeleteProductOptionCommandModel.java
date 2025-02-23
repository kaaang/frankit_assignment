package kr.co.frankit_assignment.api.product.application.command.model;

import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class DeleteProductOptionCommandModel implements CommandModel {
    @NonNull private UUID productId;
    @NonNull private UUID userId;
    @NonNull private UUID optionId;
}
