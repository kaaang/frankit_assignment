package kr.co.frankit_assignment.api.product.application.command.model;

import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.CommandModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class UpdateProductCommandModel implements CommandModel {
    @NonNull private UUID id;
    @NonNull private String name;
    @NonNull private UUID userId;
    private String description;
    private int shippingFee;
    private int price;
}
