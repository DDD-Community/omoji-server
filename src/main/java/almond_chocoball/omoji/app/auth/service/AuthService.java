package almond_chocoball.omoji.app.auth.service;

import almond_chocoball.omoji.app.auth.client.NaverClient;
import almond_chocoball.omoji.app.auth.config.jwt.JwtTokenProvider;
import almond_chocoball.omoji.app.auth.config.jwt.JwtValidation;
import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.auth.dto.OAuthAttributes;
import almond_chocoball.omoji.app.auth.dto.request.RefreshRequest;
import almond_chocoball.omoji.app.auth.dto.Token;
import almond_chocoball.omoji.app.auth.enums.Role;
import almond_chocoball.omoji.app.auth.enums.Social;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.member.repository.MemberRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final JwtValidation jwtValidation;
    private final NaverClient naverClient;

    public Token login(Social provider, String socialToken) {
        OAuthAttributes oAuthAttributes = getUserProfile(provider, socialToken);

        Member member = joinOrLogin(oAuthAttributes); //회원가입 혹은 로그인
        CustomUserDetails userDetails = CustomUserDetails.create(member);//UserDetails&&Principal 구현체 생성

        return tokenProvider.generateToken(userDetails);
    }

    private OAuthAttributes getUserProfile(Social provider, String socialToken) {
        Map<String, Object> userAttributes = getOAuthProfile(provider, socialToken); //accessToken에 따라 제공된 user정보Map 응답
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(provider, userAttributes); //provider, 속성key, value넘겨 속성 정보 클래스 반환
        //accesstoken으로 받은 정보들을 속성 객체로 반환

        if (oAuthAttributes.getEmail().isEmpty()) {
            throw new AuthenticationServiceException("Empty Email") {
            };
        }
        return oAuthAttributes;
    }

    private Map<String, Object> getOAuthProfile(Social provider, String socialToken) {
        switch (provider) {
            case naver:
                return naverClient.getOAuthAttributes(socialToken);
            default:
                throw new RuntimeException("getOAuthProfile 실패");
        }
    }

    //회원가입 혹은 로그인
    private Member joinOrLogin(OAuthAttributes attributes) {
        log.info("[saveOrUpdate] socialid: {}", attributes.getSocialId());
        Optional<Member> optionalMember = memberRepository.findBysocialId(attributes.getSocialId());
        Member member;
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        } else  {
            member = attributes.toEntity(Role.USER);
            return memberRepository.save(member); //없으면 새로 생성
        }
    }

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
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String socialId = userDetails.getSocialId();

        //3. Refresh Token DB와 Match
        String savedToken = memberRepository.getRefreshTokenBySocialId(socialId);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new JwtException("Refresh Token Not Matched");
        }

        //4. JWT 갱신
        Token token = tokenProvider.generateToken(userDetails);
        return token;

    }


    public SimpleSuccessResponse logout(HttpServletRequest request) { //refreshToken비움 & accessToken만료시킴(redis통해)
        final String accessToken = tokenProvider.resolveToken(request);
        final String socialId = tokenProvider.getSocialId(accessToken);

//        Long expiration = tokenProvider.getExpiration(accessToken);
//        redisTemplate.opsForValue() //JWT Expiration될 때까지 Redis에 저장 -> accessToken만료
//                .set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        memberRepository.updateRefreshToken(socialId, null); //refreshToken 비움
        return new SimpleSuccessResponse(null);
    }

    @Transactional(readOnly = true)
    public Long getMemberId(String socialId) {

        Member member = memberRepository.findBysocialId(socialId)
                .orElseThrow(()->{ throw new NoSuchElementException("User Not Found");});

        return member.getId();

    }
}