package kr.co.frankit_assignment.api.kernel.presentation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpApiResponse<T> {
    private T data;
    private Map<String, Object> meta;

    public HttpApiResponse(T data, Map<String, Object> meta) {
        this.data = data;
        this.meta = meta;
    }

    public static <T> HttpApiResponse<T> of(T data, Map<String, Object> meta) {
        return new HttpApiResponse<>(data, meta);
    }

    public static <T> HttpApiResponse<T> of(T data) {
        return new HttpApiResponse<>(data, null);
    }
}
