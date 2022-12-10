package almond_chocoball.omoji.app.post.dto.response;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class DetailPost {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private List<String> imgs;  //img_url만 받아라

    @NotNull
    private int likeCount;

    @NotNull
    private int dislikeCount;

    public static DetailPost of(Post post) { //Entity->Dto
        DetailPost detailPost = CustomObjectMapper.to(post, DetailPost.class);
        return detailPost;
    }
}
