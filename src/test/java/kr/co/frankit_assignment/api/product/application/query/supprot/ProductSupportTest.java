package kr.co.frankit_assignment.api.product.application.query.supprot;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.application.query.field.ProductsField;
import kr.co.frankit_assignment.config.QueryDslTestConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@Import(ProductSupport.class)
class ProductSupportTest extends QueryDslTestConfig {
    @Autowired private ProductSupport productSupport;

    @Nested
    @Sql(scripts = {"classpath:/test/product/find-all-by.sql"})
    class FindAllByTest {
        @Test
        void shouldBeReturnProducts() {
            var field = ProductsField.builder().size(5).build();

            var result = productSupport.findAllBy(field);

            assertThat(result.size()).isEqualTo(5);
        }

        @Test
        void shouldBeReturnProducts_WhenUsedLastViewField() {
            var dateTimeString = "2025-02-23T02:18:17.109+0900";
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXX");
            var targetTime = OffsetDateTime.parse(dateTimeString, formatter).toLocalDateTime();

            var field =
                    ProductsField.builder()
                            .size(5)
                            .lastViewedId(UUID.fromString("a0fdd4f8-9abb-4769-afe0-c2826de9a3eb"))
                            .lastViewedAt(targetTime)
                            .build();

            var result = productSupport.findAllBy(field);

            assertThat(result.size()).isEqualTo(5);
        }
    }
}
