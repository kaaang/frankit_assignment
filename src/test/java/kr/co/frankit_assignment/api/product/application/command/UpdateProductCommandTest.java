package kr.co.frankit_assignment.api.product.application.command;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.command.model.UpdateProductCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.config.UnitTestConfig;
import kr.co.frankit_assignment.core.product.Product;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UpdateProductCommandTest extends UnitTestConfig {
    @InjectMocks private UpdateProductCommand command;

    @Mock private ProductWriteRepository productWriteRepository;
    @Mock private UpdateProductCommandModel model;

    @Test
    void shouldThrowProductNotFoundException_WhenProductNotFound() {
        given(productWriteRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> command.execute(model)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void shouldThrowProductAccessDeniedException_WhenNotEqualsCreatedIdAndModelId() {
        var product = mock(Product.class);
        given(product.getCreatedBy()).willReturn(UUID.randomUUID());
        given(productWriteRepository.findById(any())).willReturn(Optional.of(product));
        given(model.getUserId()).willReturn(UUID.randomUUID());

        assertThatThrownBy(() -> command.execute(model))
                .isInstanceOf(ProductAccessDeniedException.class);
    }

    @Test
    void shouldCallSave() {
        var userId = UUID.randomUUID();
        var productId = UUID.randomUUID();
        given(model.getUserId()).willReturn(userId);
        given(model.getId()).willReturn(productId);
        given(model.getName()).willReturn("Test");
        given(model.getDescription()).willReturn("Test");
        given(model.getPrice()).willReturn(1000);
        given(model.getShippingFee()).willReturn(3000);

        var product = mock(Product.class);
        given(productWriteRepository.findById(any())).willReturn(Optional.of(product));
        given(product.getCreatedBy()).willReturn(userId);

        command.execute(model);

        then(productWriteRepository).should(times(1)).save(any());
    }
}
