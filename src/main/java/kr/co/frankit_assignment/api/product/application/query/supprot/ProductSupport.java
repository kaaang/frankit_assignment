package kr.co.frankit_assignment.api.product.application.query.supprot;

import static kr.co.frankit_assignment.core.product.QProduct.*;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.query.field.ProductsField;
import kr.co.frankit_assignment.core.product.Product;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductSupport {
    private final JPAQueryFactory queryFactory;

    public List<Product> findAllBy(@NonNull ProductsField field) {
        return queryFactory
                .selectFrom(product)
                .where(product.deletedAt.isNull())
                .where(this.lastViewedAtCondition(field.getLastViewedId(), field.getLastViewedAt()))
                .orderBy(product.createdAt.desc(), product.id.asc())
                .limit(field.getSize())
                .fetch();
    }

    public Optional<Product> findWithOptionById(@NonNull UUID id) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(product)
                        .leftJoin(product.options)
                        .fetchJoin()
                        .where(product.deletedAt.isNull())
                        .where(product.id.eq(id))
                        .fetchFirst());
    }

    private BooleanBuilder lastViewedAtCondition(UUID lastViewedId, LocalDateTime lastViewedAt) {
        var builder = new BooleanBuilder();
        if (Objects.isNull(lastViewedId) || Objects.isNull(lastViewedAt)) {
            return builder;
        }

        return builder.and(
                product
                        .createdAt
                        .lt(lastViewedAt)
                        .or(product.createdAt.eq(lastViewedAt).and(product.id.gt(lastViewedId))));
    }
}
