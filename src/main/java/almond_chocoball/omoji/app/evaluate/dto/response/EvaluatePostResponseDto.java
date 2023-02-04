package almond_chocoball.omoji.app.evaluate.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EvaluatePostResponseDto {
    private Long id;
    private String title;
    private List<String> imgs;
    private List<String> hashtags;

    public static EvaluatePostResponseDto of(Post post) {
        return CustomObjectMapper.to(post, EvaluatePostResponseDto.class);
    }
}
