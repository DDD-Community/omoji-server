package almond_chocoball.omoji.app.auth.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component //로그인 실패 시 진입
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e)
            throws IOException, ServletException {
        log.error(e.getMessage());
        writeTokenResponse(response, e.getMessage());
    }
    private void writeTokenResponse(HttpServletResponse response, String msg)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(msg);
        writer.flush();
    }
}