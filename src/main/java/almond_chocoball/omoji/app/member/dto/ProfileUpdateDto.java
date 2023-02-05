package almond_chocoball.omoji.app.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDto {

    @NotBlank
    @Size(min=1, max=20)
    private String nickname;

//    public static ProfileUpdateDto of(Member member) { //Entity->Dto
//        ProfileUpdateDto profileUpdateDto = CustomObjectMapper.to(member, ProfileUpdateDto.class);
//        return profileUpdateDto;
//    }
}
