package almond_chocoball.omoji.app.auth.dto;

import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Token {

    private String accessToken;
    private String refreshToken;

}

