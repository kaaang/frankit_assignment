package kr.co.frankit_assignment.api.product.application.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.config.UnitTestConfig;
import kr.co.frankit_assignment.core.product.repository.read.ProductReadRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ProductQueryTest extends UnitTestConfig {
    @InjectMocks private ProductQuery query;

    @Mock private ProductReadRepository productReadRepository;

    @Nested
    class getOr404ByIdTest {
        @Test
        void shouldThrowProductNotFoundException_WhenProductIsEmpty() {
            given(productReadRepository.findByIdAndDeletedAtIsNull(any())).willReturn(Optional.empty());

            assertThatThrownBy(() -> query.getOr404ById(UUID.randomUUID()))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }
}
