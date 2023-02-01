package almond_chocoball.omoji.app.auth.config.handler;

import almond_chocoball.omoji.app.auth.config.HttpCookieOAuth2RequestRepository;
import almond_chocoball.omoji.app.auth.config.jwt.JwtTokenProvider;
import almond_chocoball.omoji.app.auth.dto.Token;
import almond_chocoball.omoji.app.common.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static almond_chocoball.omoji.app.auth.config.HttpCookieOAuth2RequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static almond_chocoball.omoji.app.common.utils.CustomObjectMapper.objectMapper;

@Slf4j
@Component //로그인 완료 시 진입
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider tokenProvider;
    private final HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;

    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            log.info("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        //getRedirectStrategy().sendRedirect(request, response, targetUrl);

        // JWT 생성
        Token token = tokenProvider.generateToken(authentication);
        writeTokenResponse(response, token);

    }
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = cookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        log.info("successHandler: targetUrl: {}", targetUrl);

        String token = String.valueOf(tokenProvider.generateToken(authentication));
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
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