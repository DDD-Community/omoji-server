package almond_chocoball.omoji.app.comment.controller;

import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.comment.dto.request.CommentRequestDto;
import almond_chocoball.omoji.app.comment.dto.response.CommentResponseDto;
import almond_chocoball.omoji.app.comment.service.CommentService;
import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluatePostResponseDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import almond_chocoball.omoji.app.member.service.MemberService;
import almond_chocoball.omoji.app.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Tag(name = "Comment",description = "댓글다는 페이지")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("posts/{postId}/comments")
    @Tag(name = "Comment")
    @Operation(summary = "특정 Post의 댓글들 불러오기", description = "댓글을 모두 불러올때 사용")
    public ResponseEntity<List<CommentResponseDto>> getCommentAll(@PathVariable("postId") Long id) {
        return ApiResponse.success(
                commentService.getCommentsByPost(
                        postService.getPostById(id)
                )
        );
    }

    @GetMapping("comments/{commentId}")
    @Tag(name = "Comment")
    @Operation(summary = "특정 댓글 불러오기", description = "한개의 댓글만 불러오기")
    public ResponseEntity<CommentResponseDto> getCommentOne(@PathVariable("commentId") Long id) {
        return ApiResponse.success(
                commentService.getCommentById(id)
        );
    }

    @PostMapping("posts/{postid}/comments")
    @Tag(name = "Comment")
    @Operation(summary = "댓글 달기", description = "PostId를 기반으로 댓글을 작성")
    public ResponseEntity<SimpleSuccessResponse> postComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                             @RequestBody CommentRequestDto commentRequestDto,
                                                             @PathVariable("postid") Long id) {
        return ApiResponse.success(
                commentService.postCommentByPostId(
                    memberService.findMember(userDetails),
                    postService.getPostById(id),
                    commentRequestDto
                )
        );
    }

    @PutMapping("posts/{postid}/comments/{commentid}")
    @Tag(name = "Comment")
    @Operation(summary = "댓글 수정하기",description = "")
    public ResponseEntity<SimpleSuccessResponse> putComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @PathVariable("postid") Long postid,
                                                            @PathVariable("commentid") Long commentid) {
        return ApiResponse.success(
                commentService.putCommentByPostId(
                        memberService.findMember(userDetails),
                        postService.getPostById(postid),
                        commentService.getComment(commentid),
                        commentRequestDto
                )
        );
    }

    @DeleteMapping("comments/{commentid}")
    @Tag(name = "Comment")
    @Operation(summary = "댓글 제거하기", description = "Post ID를 입력받고 좋아요(LIKE), 싫어요(DISLIKE), 넘어가기(PASS)를 선택하여 값 감소")
    public ResponseEntity<SimpleSuccessResponse> deleteComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                               @PathVariable("commentid") Long id){
        return ApiResponse.success(
                commentService.deleteCommentById(
                    memberService.findMember(userDetails), commentService.getComment(id)
                )
        );
    }
}
