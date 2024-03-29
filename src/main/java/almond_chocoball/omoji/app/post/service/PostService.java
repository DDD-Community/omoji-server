package almond_chocoball.omoji.app.post.service;

import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostPagingResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostsResponseDto;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Post getPostById(Long id);

    SimpleSuccessResponse uploadPost(Member member, PostRequestDto postRequestDto, List<MultipartFile> imgFileList);

    DetailPostResponseDto getPost(Member member, Long id);

    PostsResponseDto<List<PostPagingResponseDto>> getMemberPostsWithPaging(Member member, int start, int limit);

    SimpleSuccessResponse removeMyPost(Member member, Long id);

    SimpleSuccessResponse updatePost(Member member, PostRequestDto postRequestDto,
                                     List<MultipartFile> imgFileList);

    void removeMyAllPosts(Member member);

    PostsResponseDto<List<PostPagingResponseDto>> getMemberPosts(Member member);
}
