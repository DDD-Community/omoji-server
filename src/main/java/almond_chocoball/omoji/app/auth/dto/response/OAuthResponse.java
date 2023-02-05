package almond_chocoball.omoji.app.auth.dto.response;

import almond_chocoball.omoji.app.auth.dto.Token;
import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OAuthResponse extends Token {
    private String nickname;
    private Boolean isNewUser;
    private Long userId;

    public static OAuthResponse of(Token token, String nickname, Boolean isNewUser, Long userId) { //Entity->Dto
        OAuthResponse authResponse = CustomObjectMapper.to(token, OAuthResponse.class);
        authResponse.nickname = nickname;
        authResponse.isNewUser = isNewUser;
        authResponse.userId = userId;
        return authResponse;
    }
}


