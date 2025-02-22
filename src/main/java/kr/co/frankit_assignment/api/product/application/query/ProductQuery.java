package kr.co.frankit_assignment.api.product.application.query;

import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.api.product.application.query.output.ProductOutput;
import kr.co.frankit_assignment.core.product.repository.read.ProductReadRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductQuery {
    private final ProductReadRepository productReadRepository;

    @Transactional(readOnly = true)
    public ProductOutput getOr404ById(@NonNull UUID id) {
        var product =
                productReadRepository
                        .findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ProductOutput.from(product);
    }
}
