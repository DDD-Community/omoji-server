package almond_chocoball.omoji.app.auth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NaverClient {

    private final WebClient webClient;
    private static final ParameterizedTypeReference<Map<String, Object>> ATTR_TYPE = new ParameterizedTypeReference<>() {};
    public Map<String, Object> getOAuthAttributes(String socialToken) {
        Map<String, Object> attributes = webClient
                .get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .headers(h -> h.setBearerAuth(socialToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> Mono.error(new AuthenticationServiceException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new AuthenticationServiceException("Internal Server Error")))
                .bodyToMono(ATTR_TYPE)
                .block();

        return attributes;
    }
}
