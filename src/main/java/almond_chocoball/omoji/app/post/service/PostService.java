package almond_chocoball.omoji.app.post.service;

import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.post.dto.response.PostPagingResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostReactionResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostsResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    SimpleSuccessResponse uploadPost(PostRequestDto postRequestDto, List<MultipartFile> imgFileList) throws Exception;

    DetailPostResponseDto getPost(Long id);

    PostReactionResponseDto setReaction(Long id, Boolean isLike, Boolean isIncrease);

    PostsResponseDto<List<PostPagingResponseDto>> getPostsWithPaging(int start, int limit);

    PostsResponseDto<List<DetailPostResponseDto>> getMyPostsWithPaging(int start, int limit);
}
