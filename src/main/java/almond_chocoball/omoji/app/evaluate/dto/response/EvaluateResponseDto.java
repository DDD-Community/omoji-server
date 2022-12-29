package almond_chocoball.omoji.app.evaluate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class EvaluateResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private Long likeCount = 0L;

    @NotNull
    private Long dislikeCount = 0L;

    @NotNull
    private Long passCount = 0L;
}