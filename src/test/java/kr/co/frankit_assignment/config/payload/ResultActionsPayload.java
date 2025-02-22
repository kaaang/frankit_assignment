package kr.co.frankit_assignment.config.payload;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Getter
@Builder
public class ResultActionsPayload<T, V> {
    private HttpMethod httpMethod;
    private String path;
    private V pathVariable;
    private UserDetails userDetails;
    private T request;

    public MockHttpServletRequestBuilder getRequestBuilder() {
        MockHttpServletRequestBuilder requestBuilder;
        if (httpMethod.equals(HttpMethod.GET)) {
            requestBuilder = (pathVariable != null) ? get(path, pathVariable) : get(path);
        } else if (httpMethod.equals(HttpMethod.POST)) {
            requestBuilder = (pathVariable != null) ? post(path, pathVariable) : post(path);
        } else if (httpMethod.equals(HttpMethod.PUT)) {
            requestBuilder = (pathVariable != null) ? put(path, pathVariable) : put(path);
        } else if (httpMethod.equals(HttpMethod.DELETE)) {
            requestBuilder = (pathVariable != null) ? delete(path, pathVariable) : delete(path);
        } else if (httpMethod.equals(HttpMethod.PATCH)) {
            requestBuilder = (pathVariable != null) ? patch(path, pathVariable) : patch(path);
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
        }

        return requestBuilder;
    }
}
