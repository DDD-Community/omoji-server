package almond_chocoball.omoji.app.auth.service;

import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.auth.dto.OAuthAttributes;
import almond_chocoball.omoji.app.auth.enums.Role;
import almond_chocoball.omoji.app.auth.enums.Social;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {//userRequest: registrationId, accessToken

        OAuth2UserService oAuth2UserService  = new DefaultOAuth2UserService(); //성공 정보 바탕으로 oauth 서비스 객체 만듦
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest); //accessToken으로 user정보 조회

        //서비스 구분을 위한 작업 (구글: qoogle, 네이버: naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Social social = Social.valueOf(registrationId);

        //provider가 제공할 user정보 속성키 가져옴
        String userAttributeKey = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        log.info("[loadUser] userNameAttributeName = {}", userAttributeKey); //naver의 경우 response

        Map<String, Object> userAttributes = oAuth2User.getAttributes();//accessToken에 따라 제공된 user정보Map 응답

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(social, userAttributes); //provider, 속성key, value넘겨 속성 정보 클래스 반환
        //accesstoken으로 받은 정보들을 속성 객체로 반환

        if (oAuthAttributes.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationException("Empty Email");
        }

        Member member = joinOrLogin(oAuthAttributes); //회원가입 혹은 로그인

        return CustomUserDetails.create(member, userAttributes); //OAuth2User구현체 생성
        //successHandler가 사용할 수 있게 반환
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

}