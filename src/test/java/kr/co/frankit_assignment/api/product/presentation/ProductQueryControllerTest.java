package kr.co.frankit_assignment.api.product.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.UUID;
import kr.co.frankit_assignment.config.TestBaseConfig;
import kr.co.frankit_assignment.config.payload.ResultActionsPayload;
import kr.co.frankit_assignment.core.product.Product;
import kr.co.frankit_assignment.core.product.ProductData;
import kr.co.frankit_assignment.core.product.ProductFactory;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

class ProductQueryControllerTest extends TestBaseConfig {
    @Autowired private ProductWriteRepository productWriteRepository;

    @Nested
    class GetProductTest {
        private Product savedProduct;

        @BeforeEach
        void setUp() {
            savedProduct =
                    new ProductFactory(
                                    ProductData.builder()
                                            .id(UUID.randomUUID())
                                            .name("test")
                                            .description("test description")
                                            .createdBy(test_one_user.getId())
                                            .price(10000)
                                            .shippingFee(3000)
                                            .build())
                            .create();

            productWriteRepository.save(savedProduct);
        }

        @Test
        void shouldBeReturn404_WhenProductNotFound() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .userDetails(test_one_user)
                            .path("/products/{id}")
                            .pathVariable(UUID.randomUUID().toString())
                            .build();

            getResultActions(payload).andExpect(status().isNotFound());
        }

        @Test
        void shouldBeReturn200() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .userDetails(test_one_user)
                            .path("/products/{id}")
                            .pathVariable(savedProduct.getId().toString())
                            .build();

            getResultActions(payload).andExpect(status().isOk());
        }
    }

    @Nested
    class GetProductsTest {
        @BeforeEach
        void setUp() {
            var products = new ArrayList<Product>();
            for (int i = 0; i < 30; i++) {
                products.add(
                        new ProductFactory(
                                        ProductData.builder()
                                                .id(UUID.randomUUID())
                                                .name("test")
                                                .description("test description")
                                                .createdBy(test_one_user.getId())
                                                .price(10000)
                                                .shippingFee(3000)
                                                .build())
                                .create());
            }

            productWriteRepository.saveAll(products);
        }

        @Test
        void shouldBeReturn200() throws Exception {
            params.add("size", "10");
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.GET)
                            .userDetails(test_one_user)
                            .path("/products")
                            .build();

            getResultActions(payload).andExpect(status().isOk());
        }
    }
}
