package kr.co.frankit_assignment.config.payload;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
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
    private V pathVariables;
    private UserDetails userDetails;
    private T request;

    public MockHttpServletRequestBuilder getRequestBuilder() {
        MockHttpServletRequestBuilder requestBuilder;

        var uriVariables =
                (pathVariables instanceof List)
                        ? ((List<?>) pathVariables).toArray()
                        : new Object[] {pathVariables};

        if (httpMethod.equals(HttpMethod.GET)) {
            requestBuilder = (pathVariables != null) ? get(path, uriVariables) : get(path);
        } else if (httpMethod.equals(HttpMethod.POST)) {
            requestBuilder = (pathVariables != null) ? post(path, uriVariables) : post(path);
        } else if (httpMethod.equals(HttpMethod.PUT)) {
            requestBuilder = (pathVariables != null) ? put(path, uriVariables) : put(path);
        } else if (httpMethod.equals(HttpMethod.DELETE)) {
            requestBuilder = (pathVariables != null) ? delete(path, uriVariables) : delete(path);
        } else if (httpMethod.equals(HttpMethod.PATCH)) {
            requestBuilder = (pathVariables != null) ? patch(path, uriVariables) : patch(path);
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
        }

        return requestBuilder;
    }
}
