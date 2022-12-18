package almond_chocoball.omoji.app.post.controller;

import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.post.dto.request.PostPagingRequestDto;
import almond_chocoball.omoji.app.post.dto.response.PostsResponseDto;
import almond_chocoball.omoji.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostsController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<PostsResponseDto<?>> getPostsWithPaging(PostPagingRequestDto pagingRequestDto) {
        return ApiResponse.success(postService.getPostsWithPaging(pagingRequestDto.getStart(), pagingRequestDto.getLimit()));
    }

    @GetMapping("/profile/posts") //내가 쓴 글만
    public ResponseEntity<PostsResponseDto<?>> getMyPostsWithPaging(PostPagingRequestDto pagingRequestDto) {
        return ApiResponse.success(postService.getMyPostsWithPaging(pagingRequestDto.getStart(), pagingRequestDto.getLimit()));
    }
}
