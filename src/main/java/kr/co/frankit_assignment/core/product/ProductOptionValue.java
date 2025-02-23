package kr.co.frankit_assignment.core.product;

import jakarta.persistence.*;
import kr.co.frankit_assignment.core.kernel.domain.BaseEntity;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "product_bcs_option_values")
public class ProductOptionValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption option;

    @Column(nullable = false)
    private String value;

    public static ProductOptionValue create(@NonNull ProductOption option, @NonNull String value) {
        return ProductOptionValue.builder().option(option).value(value).build();
    }
}
