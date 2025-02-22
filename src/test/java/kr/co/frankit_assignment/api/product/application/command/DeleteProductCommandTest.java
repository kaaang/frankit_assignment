package kr.co.frankit_assignment.api.product.application.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.command.model.DeleteProductCommandModel;
import kr.co.frankit_assignment.api.product.application.exception.ProductAccessDeniedException;
import kr.co.frankit_assignment.api.product.application.exception.ProductNotFoundException;
import kr.co.frankit_assignment.config.UnitTestConfig;
import kr.co.frankit_assignment.core.product.Product;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class DeleteProductCommandTest extends UnitTestConfig {
    @InjectMocks private DeleteProductCommand command;

    @Mock private ProductWriteRepository productWriteRepository;
    @Mock private DeleteProductCommandModel model;

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
    void shouldCallRemove() {
        var userId = UUID.randomUUID();
        var productId = UUID.randomUUID();
        given(model.getUserId()).willReturn(userId);
        given(model.getId()).willReturn(productId);

        var product = mock(Product.class);
        given(productWriteRepository.findById(any())).willReturn(Optional.of(product));
        given(product.getCreatedBy()).willReturn(userId);

        command.execute(model);

        then(product).should(times(1)).remove(any());
    }
}
