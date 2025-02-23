package kr.co.frankit_assignment.api.product.application.command;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.api.kernel.exception.ProductOptionLimitExceededException;
import kr.co.frankit_assignment.api.product.application.command.model.CreateProductOptionCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.config.UnitTestConfig;
import kr.co.frankit_assignment.core.product.Product;
import kr.co.frankit_assignment.core.product.ProductOption;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CreateProductOptionCommandTest extends UnitTestConfig {
    @InjectMocks private CreateProductOptionCommand command;

    @Mock private ProductWriteRepository productWriteRepository;
    @Mock private CreateProductOptionCommandModel model;

    @Test
    void shouldThrowProductNotFoundException() {
        given(productWriteRepository.findWithOptionsByProductId(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> command.execute(model)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void shouldThrowProductOptionLimitExceededException_WhenProductHasMaxOptions() {
        given(model.getProductId()).willReturn(UUID.randomUUID());

        var product = mock(Product.class);
        given(productWriteRepository.findWithOptionsByProductId(any()))
                .willReturn(Optional.of(product));
        this.willReturnOptions(product);

        assertThatThrownBy(() -> command.execute(model))
                .isInstanceOf(ProductOptionLimitExceededException.class);
    }

    @Test
    void shouldThrowProductAccessDeniedException_WhenNotEqualsCreatedByAndUserId() {
        given(model.getProductId()).willReturn(UUID.randomUUID());
        given(model.getUserId()).willReturn(UUID.randomUUID());

        var product = mock(Product.class);
        given(productWriteRepository.findWithOptionsByProductId(any()))
                .willReturn(Optional.of(product));
        given(product.getCreatedBy()).willReturn(UUID.randomUUID());

        assertThatThrownBy(() -> command.execute(model))
                .isInstanceOf(ProductAccessDeniedException.class);
    }

    @Test
    void shouldCallSave() {
        var userId = UUID.randomUUID();

        var product = mock(Product.class);
        given(productWriteRepository.findWithOptionsByProductId(any()))
                .willReturn(Optional.of(product));
        given(product.getCreatedBy()).willReturn(userId);

        given(model.getName()).willReturn("test");
        given(model.getProductId()).willReturn(UUID.randomUUID());
        given(model.getUserId()).willReturn(userId);
        given(model.getType()).willReturn(OptionType.TEXT);
        given(model.getExtraPrice()).willReturn(1000);

        command.execute(model);

        then(productWriteRepository).should(times(1)).save(any());
    }

    void willReturnOptions(Product product) {
        var option1 = mock(ProductOption.class);
        var option2 = mock(ProductOption.class);
        var option3 = mock(ProductOption.class);

        given(product.getOptions()).willReturn(List.of(option1, option2, option3));
    }
}
