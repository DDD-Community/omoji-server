package almond_chocoball.omoji.app.post.service;

import almond_chocoball.omoji.app.post.entity.Img;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgService {

    void uploadImg(Img img, MultipartFile imgFile) throws Exception;

    List<String> getImgUrls(Post post);

}
