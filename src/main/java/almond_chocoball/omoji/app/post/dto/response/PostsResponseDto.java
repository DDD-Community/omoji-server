package almond_chocoball.omoji.app.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostsResponseDto<T> {
    private T posts;
}
