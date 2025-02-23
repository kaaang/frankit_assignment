package kr.co.frankit_assignment.api.product.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductCreateRequest;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductOptionRequest;
import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductUpdateRequest;
import kr.co.frankit_assignment.config.TestBaseConfig;
import kr.co.frankit_assignment.config.payload.ResultActionsPayload;
import kr.co.frankit_assignment.core.product.Product;
import kr.co.frankit_assignment.core.product.ProductData;
import kr.co.frankit_assignment.core.product.ProductFactory;
import kr.co.frankit_assignment.core.product.repository.write.ProductWriteRepository;
import kr.co.frankit_assignment.core.product.vo.OptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

class ProductCommandControllerTest extends TestBaseConfig {
    @Autowired private ProductWriteRepository productWriteRepository;

    @Nested
    class CreateProductTest {
        @Test
        void shouldBeReturn400Error_WhenNameIsEmpty() throws Exception {
            var request =
                    ProductCreateRequest.builder()
                            .description("test description")
                            .price(10000)
                            .shippingFee(3000)
                            .build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.POST)
                            .userDetails(test_one_user)
                            .path("/products")
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isBadRequest());
        }

        @Test
        void shouldBeReturn201() throws Exception {
            var request =
                    ProductCreateRequest.builder()
                            .name("test")
                            .description("test description")
                            .price(10000)
                            .shippingFee(3000)
                            .build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.POST)
                            .userDetails(test_one_user)
                            .path("/products")
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isCreated());
        }
    }

    @Nested
    class UpdateProductTest {
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
        void shouldBeReturn404Error_WhenNotFoundProductId() throws Exception {
            var request =
                    ProductUpdateRequest.builder()
                            .name("test1")
                            .description("test description")
                            .price(10000)
                            .shippingFee(3000)
                            .build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.PUT)
                            .userDetails(test_one_user)
                            .path("/products/{id}")
                            .pathVariable(UUID.randomUUID().toString())
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isNotFound());
        }

        @Test
        void shouldBeReturn409Error_WhenUpdateOtherUser() throws Exception {
            var request =
                    ProductUpdateRequest.builder()
                            .name("test1")
                            .description("test description")
                            .price(10000)
                            .shippingFee(3000)
                            .build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.PUT)
                            .userDetails(test_two_user)
                            .path("/products/{id}")
                            .pathVariable(savedProduct.getId().toString())
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isForbidden());
        }

        @Test
        void shouldBeReturn200() throws Exception {
            var request =
                    ProductUpdateRequest.builder()
                            .name("test1")
                            .description("test description")
                            .price(10000)
                            .shippingFee(3000)
                            .build();
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.PUT)
                            .userDetails(test_one_user)
                            .path("/products/{id}")
                            .pathVariable(savedProduct.getId().toString())
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isOk());
        }
    }

    @Nested
    class DeleteProductTest {
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
        void shouldBeReturn404Error_WhenNotFoundProductId() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.DELETE)
                            .userDetails(test_one_user)
                            .path("/products/{id}")
                            .pathVariable(UUID.randomUUID().toString())
                            .build();

            getResultActions(payload).andExpect(status().isNotFound());
        }

        @Test
        void shouldBeReturn409Error_WhenUpdateOtherUser() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.DELETE)
                            .userDetails(test_two_user)
                            .path("/products/{id}")
                            .pathVariable(savedProduct.getId().toString())
                            .build();

            getResultActions(payload).andExpect(status().isForbidden());
        }

        @Test
        void shouldBeReturn200() throws Exception {
            var payload =
                    ResultActionsPayload.builder()
                            .httpMethod(HttpMethod.DELETE)
                            .userDetails(test_one_user)
                            .path("/products/{id}")
                            .pathVariable(savedProduct.getId().toString())
                            .build();

            getResultActions(payload).andExpect(status().isOk());
        }

        @Nested
        class CreateOptionTest {
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
            void shouldReturn400Error_WhenSELECT_TYPE_AndEmptyValues() throws Exception {
                var request =
                        ProductOptionRequest.builder()
                                .name("test")
                                .type(OptionType.SELECT)
                                .extraPrice(100)
                                .build();

                var payload =
                        ResultActionsPayload.builder()
                                .httpMethod(HttpMethod.POST)
                                .userDetails(test_one_user)
                                .path("/products/{id}/options")
                                .pathVariable(savedProduct.getId().toString())
                                .request(request)
                                .build();

                getResultActions(payload).andExpect(status().isBadRequest());
            }

            @Test
            void shouldReturn400Error_WhenTEXT_TYPE_AndHasValues() throws Exception {
                var request =
                        ProductOptionRequest.builder()
                                .name("test")
                                .type(OptionType.TEXT)
                                .values(List.of("test"))
                                .extraPrice(100)
                                .build();

                var payload =
                        ResultActionsPayload.builder()
                                .httpMethod(HttpMethod.POST)
                                .userDetails(test_one_user)
                                .path("/products/{id}/options")
                                .pathVariable(savedProduct.getId().toString())
                                .request(request)
                                .build();

                getResultActions(payload).andExpect(status().isBadRequest());
            }

            @Test
            void shouldReturnOk() throws Exception {
                var request =
                        ProductOptionRequest.builder()
                                .name("test")
                                .type(OptionType.TEXT)
                                .extraPrice(100)
                                .build();

                var payload =
                        ResultActionsPayload.builder()
                                .httpMethod(HttpMethod.POST)
                                .userDetails(test_one_user)
                                .path("/products/{id}/options")
                                .pathVariable(savedProduct.getId().toString())
                                .request(request)
                                .build();

                getResultActions(payload).andExpect(status().isCreated());
            }
        }
    }
}
