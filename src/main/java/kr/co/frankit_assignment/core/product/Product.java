package kr.co.frankit_assignment.core.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import kr.co.frankit_assignment.core.kernel.domain.BaseEntityAggregateRoot;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "product_bcs_products")
public class Product extends BaseEntityAggregateRoot<Product> {
    @Id private UUID id;

    @Column private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column private int price;

    @Column private int shippingFee;

    @Column private UUID createdBy;

    public void remove(@NonNull LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
