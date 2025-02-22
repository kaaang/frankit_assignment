package kr.co.frankit_assignment.core.product.repository.read;

import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.core.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReadRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByIdAndDeletedAtIsNull(UUID id);
}
