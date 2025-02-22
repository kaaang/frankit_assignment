package kr.co.frankit_assignment.api.product.application.command;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import kr.co.frankit_assignment.api.kernel.command.Command;
import kr.co.frankit_assignment.api.product.application.command.model.DeleteProductCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductCommand implements Command<DeleteProductCommandModel> {
    private final ProductWriteRepository productWriteRepository;

    @Override
    @Transactional
    public void execute(DeleteProductCommandModel model) {
        var product =
                productWriteRepository
                        .findById(model.getId())
                        .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (!product.getCreatedBy().equals(model.getUserId())) {
            throw new ProductAccessDeniedException("상품을 삭제할 권한이 없습니다.");
        }

        product.remove(LocalDateTime.now());
    }
}
