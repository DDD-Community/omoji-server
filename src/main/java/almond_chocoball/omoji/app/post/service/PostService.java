package almond_chocoball.omoji.app.post.service;

import almond_chocoball.omoji.app.post.dto.request.PostDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPost;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Long uploadPost(PostDto postDto, List<MultipartFile> imgFileList) throws Exception;

    DetailPost getPost(Long id);

}
