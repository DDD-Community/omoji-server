package almond_chocoball.omoji.app.post.controller;

import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.post.dto.response.PostReactionResponseDto;
import almond_chocoball.omoji.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostReactionController {

    private final PostService postService;

    @PostMapping("/{id}/like")
    public ResponseEntity<PostReactionResponseDto> increaseLike(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, true, true));
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<PostReactionResponseDto> increaseDislike(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, false, true));
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<PostReactionResponseDto> decreaseLike(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, true, false));
    }

    @DeleteMapping("/{id}/dislike")
    public ResponseEntity<PostReactionResponseDto> decreaseDislike(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.setReaction(id, false, false));
    }

}
