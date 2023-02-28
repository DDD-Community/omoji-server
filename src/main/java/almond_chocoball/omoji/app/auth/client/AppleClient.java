package almond_chocoball.omoji.app.auth.client;

import almond_chocoball.omoji.app.auth.dto.apple.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AppleClient {

    private final WebClient webClient;
    private static final ParameterizedTypeReference<Keys> ATTR_TYPE = new ParameterizedTypeReference<>() {};
    public Keys getPublicKeys() {
        Keys keys = webClient
                .get()
                .uri("https://appleid.apple.com/auth/keys")
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new AuthenticationServiceException("Internal Server Error")))
                .bodyToMono(ATTR_TYPE)
                .block();

        return keys;
    }
}
