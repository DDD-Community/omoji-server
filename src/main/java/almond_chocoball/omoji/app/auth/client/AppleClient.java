package almond_chocoball.omoji.app.auth.client;

import almond_chocoball.omoji.app.auth.dto.apple.Keys;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AppleClient extends SocialClient<Keys> {
    private static final ParameterizedTypeReference<Keys> ATTR_TYPE = new ParameterizedTypeReference<>() {};
    public AppleClient(WebClient webClient) {
        super(webClient);
    }

    @Override
    protected String getBaseUri() {
        return "https://appleid.apple.com/auth/keys";
    }

    @Override
    protected ParameterizedTypeReference<Keys> getAttrType() {
        return ATTR_TYPE;
    }
}
