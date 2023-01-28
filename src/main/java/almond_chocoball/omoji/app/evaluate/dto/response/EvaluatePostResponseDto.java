package almond_chocoball.omoji.app.evaluate.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class EvaluatePostResponseDto {
    private Long id;
    private String title;
    private List<String> imgs;

    public static EvaluatePostResponseDto of(Post post) {
        return CustomObjectMapper.to(post, EvaluatePostResponseDto.class);
    }
}
