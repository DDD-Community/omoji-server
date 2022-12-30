package almond_chocoball.omoji.app.auth.service;

import almond_chocoball.omoji.app.auth.config.jwt.JwtTokenProvider;
import almond_chocoball.omoji.app.auth.config.jwt.JwtValidation;
import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.auth.dto.RefreshRequest;
import almond_chocoball.omoji.app.auth.dto.Token;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.member.repository.MemberRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final JwtValidation jwtValidation;

    @Transactional
    public Token refreshToken(RefreshRequest refreshRequest) { //access토큰 만료 시 클라가 요청

        final String oldAccessToken = refreshRequest.getAccessToken();
        final String oldRefreshToken = refreshRequest.getRefreshToken();

        //1. Validate Refresh Token
        if (!jwtValidation.validateToken(oldRefreshToken)) { //둘 다 만료 -> 재로그인 필요
            throw new JwtException("JWT Expired");
        }

        //2. 유저 정보 얻기
//        if (!tokenProvider.checkBlackList(oldAccessToken)) { //탈퇴/로그아웃한 유저
//            throw new JwtException("Must SignUp or SignIn to Refresh AccessToken");
//        }

        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
        CustomUserDetails member = (CustomUserDetails) authentication.getPrincipal();

        String socialId = member.getSocialId();

        //3. Refresh Token DB와 Match
        String savedToken = memberRepository.getRefreshTokenBySocialId(socialId);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new JwtException("Refresh Token Not Matched");
        }

        //4. JWT 갱신
        Token token = tokenProvider.generateToken(authentication);
        return token;

    }

    @Transactional
    public SimpleSuccessResponse logout(HttpServletRequest request) { //refreshToken비움 & accessToken만료시킴(redis통해)
        final String accessToken = tokenProvider.resolveToken(request);
        final String socialId = tokenProvider.getSocialId(accessToken);

//        Long expiration = tokenProvider.getExpiration(accessToken);
//        redisTemplate.opsForValue() //JWT Expiration될 때까지 Redis에 저장 -> accessToken만료
//                .set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        memberRepository.updateRefreshToken(socialId, null); //refreshToken 비움
        return new SimpleSuccessResponse(null);
    }

    public Long getMemberId(String socialId) {

        Member member = memberRepository.findBysocialId(socialId)
                .orElseThrow(()->{ throw new NoSuchElementException("User Not Found");});

        return member.getId();

    }
}