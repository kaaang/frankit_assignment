package kr.co.frankit_assignment.api.product.application.command;

import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.command.Command;
import kr.co.frankit_assignment.api.kernel.exception.ProductOptionLimitExceededException;
import kr.co.frankit_assignment.api.product.application.command.model.CreateProductOptionCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.core.product.ProductOption;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateProductOptionCommand implements Command<CreateProductOptionCommandModel> {
    private static final int MAX_OPTIONS_SIZE = 3;

    private final ProductWriteRepository productWriteRepository;

    @Override
    @Transactional
    public void execute(CreateProductOptionCommandModel model) {
        var product =
                productWriteRepository
                        .findWithOptionsByProductId(model.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (product.getOptions().size() >= MAX_OPTIONS_SIZE) {
            throw new ProductOptionLimitExceededException("옵션을 더이상 추가할 수 없습니다.");
        }

        if (!product.getCreatedBy().equals(model.getUserId())) {
            throw new ProductAccessDeniedException("상품을 수정할 권한이 없습니다.");
        }

        var option =
                ProductOption.create(
                        UUID.randomUUID(),
                        product,
                        model.getName(),
                        model.getType(),
                        model.getValues(),
                        model.getExtraPrice());
        product.addOption(option);

        productWriteRepository.save(product);
    }
}
