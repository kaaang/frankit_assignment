package kr.co.frankit_assignment.api.product.application.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.command.model.UpdateProductOptionCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.api.product.application.exception.ProductOptionNotFoundException;
import kr.co.frankit_assignment.config.UnitTestConfig;
import kr.co.frankit_assignment.core.product.Product;
import kr.co.frankit_assignment.core.product.ProductOption;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UpdateProductOptionCommandTest extends UnitTestConfig {
    @InjectMocks private UpdateProductOptionCommand command;

    @Mock private ProductWriteRepository productWriteRepository;
    @Mock private UpdateProductOptionCommandModel model;

    @Test
    void shouldThrowProductNotFoundException() {
        given(productWriteRepository.findWithOptionsByProductIdAndOptionId(any(), any()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> command.execute(model)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void shouldThrowProductAccessDeniedException_WhenNotEqualsCreatedByAndUserId() {
        given(model.getProductId()).willReturn(UUID.randomUUID());
        given(model.getUserId()).willReturn(UUID.randomUUID());
        given(model.getOptionId()).willReturn(UUID.randomUUID());

        var product = mock(Product.class);
        given(productWriteRepository.findWithOptionsByProductIdAndOptionId(any(), any()))
                .willReturn(Optional.of(product));
        given(product.getCreatedBy()).willReturn(UUID.randomUUID());

        assertThatThrownBy(() -> command.execute(model))
                .isInstanceOf(ProductAccessDeniedException.class);
    }

    @Test
    void shouldThrowProductOptionNotFoundException_WhenOptionIsEmpty() {
        var userId = UUID.randomUUID();
        given(model.getProductId()).willReturn(UUID.randomUUID());
        given(model.getUserId()).willReturn(userId);
        given(model.getOptionId()).willReturn(UUID.randomUUID());

        var product = mock(Product.class);
        given(productWriteRepository.findWithOptionsByProductIdAndOptionId(any(), any()))
                .willReturn(Optional.of(product));
        given(product.getCreatedBy()).willReturn(userId);
        given(product.getOptions()).willReturn(List.of());

        assertThatThrownBy(() -> command.execute(model))
                .isInstanceOf(ProductOptionNotFoundException.class);
    }

    @Test
    void shouldCallSave() {
        var userId = UUID.randomUUID();

        var product = mock(Product.class);
        var option = mock(ProductOption.class);
        given(product.getOptions()).willReturn(List.of(option));
        given(productWriteRepository.findWithOptionsByProductIdAndOptionId(any(), any()))
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
}
