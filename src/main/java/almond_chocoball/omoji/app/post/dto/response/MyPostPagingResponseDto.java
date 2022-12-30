package almond_chocoball.omoji.app.post.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    public static MyPostPagingResponseDto of(Post post) { //Entity->Dto
        MyPostPagingResponseDto myPostPagingResponseDto = CustomObjectMapper.to(post, MyPostPagingResponseDto.class);
        return myPostPagingResponseDto;
    }
}

