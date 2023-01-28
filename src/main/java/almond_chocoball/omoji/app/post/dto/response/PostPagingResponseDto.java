package almond_chocoball.omoji.app.post.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.hashtag.dto.response.HashtagResponseDto;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPagingResponseDto {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private List<String> imgs;

    private List<HashtagResponseDto> hashtags;

    public static PostPagingResponseDto of(Post post) {
        PostPagingResponseDto postPagingResponseDto = CustomObjectMapper.to(post, PostPagingResponseDto.class);
        postPagingResponseDto.hashtags = post.getHashtagPosts().stream()
                .map(hashtagPost -> new HashtagResponseDto(hashtagPost))
                .collect(Collectors.toList());
        return postPagingResponseDto;
    }

//    public static List<PostPagingResponseDto> of(List<Post> postList) { //Entity->Dto
//
//        return postList.stream().map(post -> {
//            PostPagingResponseDto postPagingResponseDto = CustomObjectMapper.to(post, PostPagingResponseDto.class);
//            return postPagingResponseDto;
//        }).collect(Collectors.toList());
//    }
}