package kr.co.frankit_assignment.api.product.application.command;

import jakarta.transaction.Transactional;
import kr.co.frankit_assignment.api.kernel.command.Command;
import kr.co.frankit_assignment.api.product.application.command.model.UpdateProductCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.core.product.ProductData;
import kr.co.frankit_assignment.core.product.ProductFactory;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductCommand implements Command<UpdateProductCommandModel> {
    private final ProductWriteRepository productWriteRepository;

    @Override
    @Transactional
    public void execute(UpdateProductCommandModel model) {
        var product =
                productWriteRepository
                        .findById(model.getId())
                        .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (!product.getCreatedBy().equals(model.getUserId())) {
            throw new ProductAccessDeniedException("상품을 수정할 권한이 없습니다.");
        }

        productWriteRepository.save(
                new ProductFactory(
                                ProductData.builder()
                                        .id(model.getId())
                                        .createdBy(product.getCreatedBy())
                                        .name(model.getName())
                                        .description(model.getDescription())
                                        .price(model.getPrice())
                                        .shippingFee(model.getShippingFee())
                                        .build())
                        .create());
    }
}
