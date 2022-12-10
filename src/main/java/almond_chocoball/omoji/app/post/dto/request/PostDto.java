package almond_chocoball.omoji.app.post.dto.request;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;

    @NotBlank
    @Size(min=1, max=38)
    private String title;

    public Post toPost() { //Dto->Entity
        Post post = CustomObjectMapper.to(this, Post.class);
        return post;
    }
}
