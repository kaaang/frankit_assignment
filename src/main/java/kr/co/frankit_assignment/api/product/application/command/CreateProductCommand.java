package kr.co.frankit_assignment.api.product.application.command;

import kr.co.frankit_assignment.api.kernel.command.Command;
import kr.co.frankit_assignment.api.product.application.command.model.CreateProductCommandModel;
import kr.co.frankit_assignment.core.product.ProductData;
import kr.co.frankit_assignment.core.product.ProductFactory;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductCommand implements Command<CreateProductCommandModel> {
    private final ProductWriteRepository productWriteRepository;

    @Override
    public void execute(CreateProductCommandModel model) {
        var product =
                new ProductFactory(
                                ProductData.builder()
                                        .id(model.getId())
                                        .name(model.getName())
                                        .description(model.getDescription())
                                        .price(model.getPrice())
                                        .shippingFee(model.getShippingFee())
                                        .createdBy(model.getCreatedBy())
                                        .build())
                        .create();

        productWriteRepository.save(product);
    }
}
