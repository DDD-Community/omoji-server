package almond_chocoball.omoji.app.auth.dto.response;

import almond_chocoball.omoji.app.auth.dto.Token;
import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OAuthResponse extends Token {
    private Boolean isNewUser;
    private Long userId;

    public static OAuthResponse of(Token token, Boolean isNewUser, Long userId) { //Entity->Dto
        OAuthResponse oAuthResponse = CustomObjectMapper.to(token, OAuthResponse.class);
        oAuthResponse.isNewUser = isNewUser;
        oAuthResponse.userId = userId;
        return oAuthResponse;
    }
}


