package almond_chocoball.omoji.app.img.dto.request;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.img.entity.Img;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImgRequestDto {

    private Long id;

    private Post post;

    public Img toImg() { //Dto->Entity
        Img img = CustomObjectMapper.to(this, Img.class);
        return img;
    }

}
