package kr.co.frankit_assignment.api.product.application.command;

import kr.co.frankit_assignment.api.kernel.command.Command;
import kr.co.frankit_assignment.api.product.application.command.model.DeleteProductOptionCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.api.product.application.exception.ProductOptionNotFoundException;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteProductOptionCommand implements Command<DeleteProductOptionCommandModel> {
    private final ProductWriteRepository productWriteRepository;

    @Override
    @Transactional
    public void execute(DeleteProductOptionCommandModel model) {
        var product =
                productWriteRepository
                        .findWithOptionsByProductIdAndOptionId(model.getProductId(), model.getOptionId())
                        .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (!product.getCreatedBy().equals(model.getUserId())) {
            throw new ProductAccessDeniedException("상품을 삭제할 권한이 없습니다.");
        }

        var option =
                product.getOptions().stream()
                        .findFirst()
                        .orElseThrow(() -> new ProductOptionNotFoundException("옵션을 찾을 수 없습니다."));

        product.removeOption(option);
        productWriteRepository.save(product);
    }
}
