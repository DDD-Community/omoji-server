package almond_chocoball.omoji.app.comment.dto.response;

import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private String comment;

    @NotNull
    private Post post;
}