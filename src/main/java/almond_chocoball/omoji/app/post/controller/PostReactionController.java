package almond_chocoball.omoji.app.post.controller;

import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.post.dto.response.PostReactionResponseDto;
import almond_chocoball.omoji.app.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Post reaction",description = "Post의 좋아요 싫어요를 관리")
public class PostReactionController {

    private final PostService postService;

    @PostMapping("/{id}/like")
    @Tag(name = "Post reaction")
    @Operation(summary = "좋아요 증가", description = "Post ID를 입력받아 좋아요를 증가시키는 API")
    public ResponseEntity<PostReactionResponseDto> increaseLike(
            @Parameter(description = "post 의 id", in = ParameterIn.PATH)
            @PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, true, true));
    }

    @PostMapping("/{id}/dislike")
    @Tag(name = "Post reaction")
    @Operation(summary = "싫어요 증가", description = "Post ID를 입력받아 싫어요를 증가시키는 API")
    public ResponseEntity<PostReactionResponseDto> increaseDislike(
            @Parameter(description = "post 의 id", in = ParameterIn.PATH)
            @PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, false, true));
    }

    @DeleteMapping("/{id}/like")
    @Tag(name = "Post reaction")
    @Operation(summary = "좋아요 감소", description = "Post ID를 입력받아 좋아요를 감소시키는 API")
    public ResponseEntity<PostReactionResponseDto> decreaseLike(
            @Parameter(description = "post 의 id", in = ParameterIn.PATH)
            @PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, true, false));
    }

    @DeleteMapping("/{id}/dislike")
    @Tag(name = "Post reaction",description = "Post의 싫어요를 감소시키는 API")
    @Operation(summary = "싫어요 감소", description = "Post ID를 입력받아 싫어요를 감소시키는 API")
    public ResponseEntity<PostReactionResponseDto> decreaseDislike(
            @Parameter(description = "post 의 id", in = ParameterIn.PATH)
            @PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, false, false));
    }

}
