package almond_chocoball.omoji.app.member.dto;

import almond_chocoball.omoji.app.auth.enums.Gender;
import almond_chocoball.omoji.app.auth.enums.Social;
import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.member.entity.Member;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileInfoDto {

    private Long id;

    private String nickname;

    private Social social;

    private String email;

    private Gender gender;

    private Short birthyear;


    public static ProfileInfoDto of(Member member) { //Entity->Dto
        ProfileInfoDto profileResponse = CustomObjectMapper.to(member, ProfileInfoDto.class);
        return profileResponse;
    }
}
