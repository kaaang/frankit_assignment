package kr.co.frankit_assignment.api.product.application.query;

import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.api.product.application.query.field.ProductsField;
import kr.co.frankit_assignment.api.product.application.query.output.ProductOptionsOutput;
import kr.co.frankit_assignment.api.product.application.query.output.ProductOutput;
import kr.co.frankit_assignment.api.product.application.query.output.ProductsOutput;
import kr.co.frankit_assignment.api.product.application.query.supprot.ProductSupport;
import kr.co.frankit_assignment.core.product.repository.read.ProductReadRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductQuery {
    private final ProductReadRepository productReadRepository;
    private final ProductSupport productSupport;

    @Transactional(readOnly = true)
    public ProductOutput getOr404ById(@NonNull UUID id) {
        var product =
                productReadRepository
                        .findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ProductOutput.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductsOutput> getProducts(@NonNull ProductsField field) {
        var products = productSupport.findAllBy(field);

        return products.stream().map(ProductsOutput::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductOptionsOutput> getProductOptions(@NonNull UUID productId) {
        var product =
                productSupport
                        .findWithOptionById(productId)
                        .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return product.getOptions().stream().map(ProductOptionsOutput::of).toList();
    }
}
