package almond_chocoball.omoji.app.hashtag.dto.response;

import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashtagResponseDto {

    private Long parent_id;

    private Long id;

    public HashtagResponseDto(HashtagPost hashtagPost) {
        this.parent_id = hashtagPost.getHashtag().getParent().getId();
        this.id = hashtagPost.getHashtag().getId();
    }
}
