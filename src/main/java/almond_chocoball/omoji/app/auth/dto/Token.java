package almond_chocoball.omoji.app.auth.dto;

import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Token {

    private String tokenPrefix;
    private String nickname;
    private String accessToken;
    private String refreshToken;

}

