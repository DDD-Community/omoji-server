package almond_chocoball.omoji.app.post.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.hashtag.dto.response.HashtagResponseDto;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostPagingResponseDto {

    private Long id;

    private int likeCount;

    private int dislikeCount;

    private List<String> imgs;

    private List<String> hashtags;

    public static PostPagingResponseDto of(Post post) { //Entity->Dto
        PostPagingResponseDto postPagingResponseDto = CustomObjectMapper.to(post, PostPagingResponseDto.class);
        postPagingResponseDto.hashtags = post.getHashtagPosts().stream()
                .map(hashtagPost -> new HashtagResponseDto(hashtagPost).getName())
                .collect(Collectors.toList());
        return postPagingResponseDto;
    }
}

