package almond_chocoball.omoji.app.auth.utils;

import almond_chocoball.omoji.app.auth.client.AppleClient;
import almond_chocoball.omoji.app.auth.dto.apple.Key;
import almond_chocoball.omoji.app.auth.dto.apple.Keys;
import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class AppleUtils {

    @Value("${apple.iss}")
    private String ISS;

    @Value("${apple.aud}")
    private String AUD;
    private final AppleClient appleClient;

    public Map<String, Object> getOAuthAttributes(String idToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(idToken);
            JWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
            if (verifyIdentityToken(signedJWT, getPayload)) { //RSA 검증
                return decodeFromIdToken(getPayload);
            }
            throw new AuthenticationServiceException("RSA 검증 실패");
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    /**
     idToken jwt 검증
     **/
    private boolean verifyIdentityToken(SignedJWT signedJWT, JWTClaimsSet payload) {
        // EXP
        Date currentTime = new Date(System.currentTimeMillis());
        if (!currentTime.before(payload.getExpirationTime())) {
            log.info("expired apple JWT");
            return false;
        }

        // NONCE(Test value) !"20B20D-0S8-1K8".equals(payload.getClaim("nonce")) || , ISS, AUD
        if (!ISS.equals(payload.getIssuer()) || !AUD.equals(payload.getAudience().get(0))) {
            log.info("apple JWT ISS, AUD not matched");
            return false;
        }

        // RSA
        if (verifyPublicKey(signedJWT)) {
            return true;
        }
        log.info("apple JWT public key not matched");
        return false;
    }

    /**
     * Apple Server에서 공개 키를 받아서 서명 확인
     * Verify the JWS E256 signature using the server’s public key
     * 3개의 공개키 중, idToken의 header영역 - kid, alg이 같은 공개키 찾아서 서명 확인
     */
    private boolean verifyPublicKey(SignedJWT signedJWT) {
        try {
            Keys keys = appleClient.getPublicKeys();
            for (Key key : keys.getKeys()) {
                RSAKey rsaKey = (RSAKey) JWK.parse(CustomObjectMapper.objectMapper.writeValueAsString(key));
                JWSVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
                if (signedJWT.verify(verifier)) {
                    log.info("verified Public Key");
                    return true;
                }
            }
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        return false;
    }

    /**
     * id_token을 decode해서 payload 값 가져오기
     */
    private Map<String, Object> decodeFromIdToken(JWTClaimsSet payload) {
        log.info("decodeFromIdToken {}", payload.toJSONObject());
        Map<String, Object> attributes = CustomObjectMapper.jsonToMap(payload.toJSONObject());
        return attributes;
    }
}