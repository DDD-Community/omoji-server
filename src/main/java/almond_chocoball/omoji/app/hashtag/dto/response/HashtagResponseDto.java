package almond_chocoball.omoji.app.hashtag.dto.response;

import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashtagResponseDto {

    private String category;

    private String hashtag;

    public HashtagResponseDto(HashtagPost hashtagPost) {
        this.category = hashtagPost.getHashtag().getParent().getName();
        this.hashtag = hashtagPost.getHashtag().getName();
    }
}
