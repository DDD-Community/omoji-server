package almond_chocoball.omoji.app.post.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.hashtag.dto.response.HashtagResponseDto;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DetailPostResponseDto extends PostPagingResponseDto{

    private Boolean isOwner = false;

    private String title;

    private String description;
    public static DetailPostResponseDto of(Post post) { //Entity->Dto
        DetailPostResponseDto detailPostResponseDto = CustomObjectMapper.to(post, DetailPostResponseDto.class);
        detailPostResponseDto.setHashtags(post.getHashtagPosts().stream()
                .map(hashtagPost -> new HashtagResponseDto(hashtagPost).getName())
                .collect(Collectors.toList()));
        return detailPostResponseDto;
    }
}
