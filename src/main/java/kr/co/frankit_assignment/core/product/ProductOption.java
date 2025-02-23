package kr.co.frankit_assignment.core.product;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.core.kernel.domain.BaseEntity;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "product_bcs_options")
public class ProductOption extends BaseEntity {
    @Id private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private OptionType type;

    @ElementCollection
    @BatchSize(size = 10)
    private List<String> values;

    private int extraPrice;

    public static ProductOption create(
            @NonNull UUID id,
            @NonNull Product product,
            @NonNull String name,
            @NonNull OptionType type,
            List<String> values,
            int extraPrice) {
        return ProductOption.builder()
                .id(id)
                .product(product)
                .name(name)
                .type(type)
                .values(values)
                .extraPrice(extraPrice)
                .build();
    }

    public void update(String name, OptionType type, List<String> values, int extraPrice) {
        this.name = name;
        this.type = type;
        this.values = values;
        this.extraPrice = extraPrice;
    }
}
