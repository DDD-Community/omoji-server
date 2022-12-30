package almond_chocoball.omoji.app.post.controller;

import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.member.service.MemberService;
import almond_chocoball.omoji.app.post.dto.request.PostPagingRequestDto;
import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostsResponseDto;
import almond_chocoball.omoji.app.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "Post를 조회하고 생성합니다")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;


    @Tag(name = "Post")
    @GetMapping("/{id}")
    @Operation(summary = "특정 id의 post 조회", description = "Post ID를 입력받아 세부 정보를 조회하는 API")
    public ResponseEntity<DetailPostResponseDto> getPost(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.getPost(id));
    }

    @Tag(name = "Post")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //img까지 첨부 - formData의 경우 @RequsetBody적지 말고, Dto에 반드시 Setter열어놔야함
    @Operation(summary = "post 생성", description = "Post를 생성하는 API")
    public ResponseEntity<SimpleSuccessResponse> uploadPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @Valid PostRequestDto postRequestDto,
                                                            @RequestParam("imgs") List<MultipartFile> imgFileList) throws Exception {
        return ApiResponse.created(postService.uploadPost(
                memberService.findMember(userDetails),
                postRequestDto, imgFileList));
    }

    @Tag(name = "Post")
    @GetMapping()
    @Operation(summary = "내가 쓴 글 빼고 start부터 limit까지 post 조회", description = "start부터 limit까지 post 조회하는 API")
    public ResponseEntity<PostsResponseDto<?>> getPostsWithPaging(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                  PostPagingRequestDto pagingRequestDto) {
        return ApiResponse.success(postService.getPostsWithPaging(memberService.findMember(userDetails), pagingRequestDto.getStart(), pagingRequestDto.getLimit()));
    }

    @Tag(name = "Post")
    @GetMapping("/profile") //내가 쓴 글만
    @Operation(summary = "start부터 limit까지 특정 user의 post 조회", description = "start부터 limit까지 특정 user의 post 조회 API")
    public ResponseEntity<PostsResponseDto<?>> getMyPostsWithPaging(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                    PostPagingRequestDto pagingRequestDto) {
        return ApiResponse.success(postService.getMyPostsWithPaging(
                memberService.findMember(userDetails),
                pagingRequestDto.getStart(), pagingRequestDto.getLimit()));
    }
}
