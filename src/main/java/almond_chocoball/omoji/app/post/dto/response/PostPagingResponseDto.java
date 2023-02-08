package almond_chocoball.omoji.app.post.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.hashtag.dto.response.HashtagResponseDto;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPostPagingResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private int likeCount;

    @NotNull
    private int dislikeCount;

    private List<String> imgs;

    private List<String> hashtags;

    public static MyPostPagingResponseDto of(Post post) { //Entity->Dto
        MyPostPagingResponseDto myPostPagingResponseDto = CustomObjectMapper.to(post, MyPostPagingResponseDto.class);
        myPostPagingResponseDto.hashtags = post.getHashtagPosts().stream()
                .map(hashtagPost -> new HashtagResponseDto(hashtagPost).getName())
                .collect(Collectors.toList());
        return myPostPagingResponseDto;
    }
}

