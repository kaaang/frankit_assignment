package kr.co.frankit_assignment.core.product.repository.write;

import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.core.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductWriteRepository extends JpaRepository<Product, UUID> {
    @Query(
            "select p from Product p left join fetch p.options o where p.id = :id and p.deletedAt is null")
    Optional<Product> findWithOptionsByProductId(UUID id);

    @Query(
            "select p from Product p inner join fetch p.options o where p.id = :id and o.id = :optionId and p.deletedAt is null")
    Optional<Product> findWithOptionsByProductIdAndOptionId(UUID id, UUID optionId);
}
