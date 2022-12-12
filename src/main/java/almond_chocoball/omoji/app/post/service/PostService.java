package almond_chocoball.omoji.app.post.service;

import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Long uploadPost(PostRequestDto postRequestDto, List<MultipartFile> imgFileList) throws Exception;

    DetailPostResponseDto getPost(Long id);

}
