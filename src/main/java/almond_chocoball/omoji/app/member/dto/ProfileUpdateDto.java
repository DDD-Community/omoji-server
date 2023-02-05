package almond_chocoball.omoji.app.member.dto;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDto {
    private String nickname;

    public static ProfileUpdateDto of(Member member) { //Entity->Dto
        ProfileUpdateDto profileUpdateDto = CustomObjectMapper.to(member, ProfileUpdateDto.class);
        return profileUpdateDto;
    }
}
