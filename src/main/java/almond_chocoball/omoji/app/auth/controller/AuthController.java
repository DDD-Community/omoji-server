package almond_chocoball.omoji.app.auth.controller;

import almond_chocoball.omoji.app.auth.dto.RefreshRequest;
import almond_chocoball.omoji.app.auth.dto.Token;
import almond_chocoball.omoji.app.auth.service.AuthService;
import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "OAuth", description = "로그아웃, 토큰 갱신")
public class AuthController {

    private final AuthService authService;
    private final String tokenPrefix = "Bearer";

    @Tag(name = "OAuth")
    @Operation(summary = "accessToken 갱신", description = "Header에 Access, Refresh 첨부")
    @PostMapping("/refresh")
    public ResponseEntity<Token> refreshToken(HttpServletRequest request) {
        final RefreshRequest refreshHeader = refreshHeader(request);
        return ApiResponse.success(authService.refreshToken(refreshHeader));
    }

    @Tag(name = "OAuth")
    @Operation(summary = "로그아웃", description = "Header에 Authorization 첨부: 해당 accessToken의 접근 막음")
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SimpleSuccessResponse> logout(HttpServletRequest request) {
        return ApiResponse.success(authService.logout(request));
    }

    //refresh시 accesstoken, refreshtoken을 전달받음
    private RefreshRequest refreshHeader(HttpServletRequest request) throws JwtException {
        String accessToken = request.getHeader("Access");
        String refreshToken = request.getHeader("Refresh");
        if (StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken) &&
                accessToken.startsWith(tokenPrefix) && accessToken.startsWith(tokenPrefix)) {
            return RefreshRequest.builder()
                    .accessToken(accessToken.substring(7))
                    .refreshToken(refreshToken.substring(7))
                    .build();
        }
        return null;
    }
}
