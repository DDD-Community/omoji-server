package almond_chocoball.omoji.app.auth.dto;

import almond_chocoball.omoji.app.auth.enums.Gender;
import almond_chocoball.omoji.app.auth.enums.Role;
import almond_chocoball.omoji.app.auth.enums.Social;
import almond_chocoball.omoji.app.member.entity.Member;
import lombok.*;

import java.util.Map;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthAttributes { //provider마다 제공해주는 정보 형태가 다르기 때문에 분기 정리 클래스
    private Map<String, Object> attributes;
    private String socialId;
    private String email;
    private String nickname;
    private Social social;
    private Gender gender;
    private Short birthyear;


    public static OAuthAttributes of(Social provider, Map<String,Object> attributes){
        //provider 판단 -> accessToken으로 받은 속성 클래스 저장
        switch (provider) {
            case naver:
                return ofNaver(attributes);
            default:
                throw new RuntimeException("OAuthAttributes 생성에 실패했습니다.");
        }
    }


    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .email((String) response.get("email"))
                .nickname((String) response.get("nickname"))
                .social(Social.naver)
                .gender(Gender.valueOf((String) response.get("gender")))
                .birthyear(Short.valueOf((String) response.get("birthyear")))
                .attributes(response)
                .socialId((String) response.get("id"))
                .build();
    }

    public Member toEntity(Role role){ //Member Entity를 리턴
        return Member.builder()
                .socialId(socialId)
                .social(social)
                .nickname(null)
                .email(email)
                .gender(gender)
                .birthyear(birthyear)
                .role(role)
                .build();
    }
}
