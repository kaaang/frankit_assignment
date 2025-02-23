package kr.co.frankit_assignment.core.product;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.core.kernel.domain.BaseEntityAggregateRoot;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 3)
    private List<ProductOption> options = new ArrayList<>();

    public void remove(@NonNull LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void addOption(@NonNull ProductOption option) {
        this.options.add(option);
    }

    public void removeOption(@NonNull ProductOption option) {
        this.options.remove(option);
    }
}
