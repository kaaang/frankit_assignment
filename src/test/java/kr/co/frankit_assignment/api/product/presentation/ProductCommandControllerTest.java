package kr.co.frankit_assignment.api.product.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.frankit_assignment.api.product.presentation.rqeuest.ProductCreateRequest;
import kr.co.frankit_assignment.config.TestBaseConfig;
import kr.co.frankit_assignment.config.payload.ResultActionsPayload;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

class ProductCommandControllerTest extends TestBaseConfig {

    @Nested
    class CreateProduct {
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
                            .userDetails(normal_user)
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
                            .userDetails(normal_user)
                            .path("/products")
                            .request(request)
                            .build();

            getResultActions(payload).andExpect(status().isCreated());
        }
    }
}
