package almond_chocoball.omoji.app.auth.config.handler;

import almond_chocoball.omoji.app.auth.config.jwt.JwtTokenProvider;
import almond_chocoball.omoji.app.auth.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static almond_chocoball.omoji.app.common.utils.CustomObjectMapper.objectMapper;

@RequiredArgsConstructor
@Component //로그인 완료 시 진입
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // JWT 생성
        Token token = tokenProvider.generateToken(authentication);
        writeTokenResponse(response, token);

    }

    private void writeTokenResponse(HttpServletResponse response, Token token)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}