package almond_chocoball.omoji.app.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PostReactionResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private Long likeCount;

    @NotNull
    private Long dislikeCount;
}