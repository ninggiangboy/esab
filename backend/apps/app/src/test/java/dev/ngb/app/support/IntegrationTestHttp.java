package dev.ngb.app.support;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Shared HTTP helpers for Spring Boot integration tests (JSON POST, error bodies on 4xx).
 */
public final class IntegrationTestHttp {

    private IntegrationTestHttp() {
    }

    /**
     * Uses JDK client so 4xx response bodies are readable; default {@link RestTemplate} + HttpURLConnection drops them.
     */
    public static RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate(new JdkClientHttpRequestFactory());
        template.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(@NonNull ClientHttpResponse response) {
                return false;
            }
        });
        return template;
    }

    public static HttpHeaders jsonRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static <T> ResponseEntity<T> postJson(
            RestTemplate restTemplate,
            String baseUrl,
            String path,
            Object body,
            Class<T> responseType
    ) {
        return restTemplate.exchange(
                baseUrl + path,
                HttpMethod.POST,
                new HttpEntity<>(body, jsonRequestHeaders()),
                responseType
        );
    }
}
