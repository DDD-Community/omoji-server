package almond_chocoball.omoji.app.post.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailPostResponseDto {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private List<String> imgs;  //img_url만 받아라

    @NotNull
    private int likeCount;

    @NotNull
    private int dislikeCount;

    public static DetailPostResponseDto of(Post post) { //Entity->Dto
        DetailPostResponseDto detailPostResponseDto = CustomObjectMapper.to(post, DetailPostResponseDto.class);
        return detailPostResponseDto;
    }
}
