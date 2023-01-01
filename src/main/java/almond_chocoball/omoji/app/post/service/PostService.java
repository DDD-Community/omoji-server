package almond_chocoball.omoji.app.post.service;

import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.post.dto.response.PostPagingResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostsResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    SimpleSuccessResponse uploadPost(Member member, PostRequestDto postRequestDto, List<MultipartFile> imgFileList) throws Exception;

    DetailPostResponseDto getPost(Long id);

    PostsResponseDto<List<PostPagingResponseDto>> getPostsWithPaging(Member member, int start, int limit);

    PostsResponseDto<List<DetailPostResponseDto>> getMyPostsWithPaging(Member member, int start, int limit);
}
