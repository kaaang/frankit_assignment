package kr.co.frankit_assignment.api.product.application.command.model;

import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class DeleteProductCommandModel implements CommandModel {
    @NonNull private UUID id;
    @NonNull private UUID userId;
}
