package almond_chocoball.omoji.app.hashtag.dto.response;

import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashtagResponseDto {

    private String name;

    public HashtagResponseDto(HashtagPost hashtagPost) {
        this.name = hashtagPost.getHashtag().getName();
    }
}
