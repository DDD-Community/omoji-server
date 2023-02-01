package almond_chocoball.omoji.app.auth.config.handler;

import almond_chocoball.omoji.app.common.utils.CookieUtils;
import almond_chocoball.omoji.app.auth.config.HttpCookieOAuth2RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static almond_chocoball.omoji.app.auth.config.HttpCookieOAuth2RequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component //로그인 실패 시 진입
@RequiredArgsConstructor
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    private final HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;
    private final CookieUtils cookieUtils;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e)
            throws IOException, ServletException {
        String targetUrl = cookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", e.getLocalizedMessage())
                .build().toUriString();

        log.info("OAuth2FailureHandler : {}, redirect_cookie_name : {}", targetUrl, REDIRECT_URI_PARAM_COOKIE_NAME);

        httpCookieOAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
        //getRedirectStrategy().sendRedirect(request, response, targetUrl);

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