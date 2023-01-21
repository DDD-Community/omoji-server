package almond_chocoball.omoji.app.img.service;

import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgService {

    void uploadImg(Post post, MultipartFile imgFileList);

    List<String> getImgUrls(Post post);

    void deleteImgs(Post post);

    void updateImg(Post post, List<MultipartFile> imgFileList);
}
