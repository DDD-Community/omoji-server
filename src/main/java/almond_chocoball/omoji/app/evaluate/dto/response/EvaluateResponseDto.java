package almond_chocoball.omoji.app.evaluate.dto.response;

import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class EvaluateResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private Post post;

    @NotNull
    private EvaluateEnum evaluateEnum;
}