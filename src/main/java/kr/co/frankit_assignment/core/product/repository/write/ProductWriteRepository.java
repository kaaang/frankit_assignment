package kr.co.frankit_assignment.core.product.repository.write;

import java.util.UUID;
import kr.co.frankit_assignment.core.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWriteRepository extends JpaRepository<Product, UUID> {}
