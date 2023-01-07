package almond_chocoball.omoji.app.evaluate.controller;

import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import almond_chocoball.omoji.app.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evaluate")
@RequiredArgsConstructor
@Tag(name = "Evaluate post",description = "Post를 평가하는 페이지")
public class EvaluateController {

    private final EvaluateService evaluateService;
    private final MemberService memberService;


    @PostMapping()
    @Tag(name = "Evaluate post")
    @Operation(summary = "평가하기", description = "Post ID를 입력받고 좋아요(LIKE), 싫어요(DISLIKE), 넘어가기(PASS)를 선택항 값 증가")
    public ResponseEntity<SimpleSuccessResponse> postEvaluate(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @RequestBody EvaluateRequestDto evaluateRequestDto) {
        return ApiResponse.success(evaluateService.insertEvaluate(
                memberService.findMember(userDetails),
                evaluateRequestDto));
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Evaluate post")
    @Operation(summary = "평가 제거하기", description = "Post ID를 입력받고 좋아요(LIKE), 싫어요(DISLIKE), 넘어가기(PASS)를 선택하여 값 감소")
    public ResponseEntity deleteEvaluate(@PathVariable("id") Long id){
        return evaluateService.removeEvaluate(id);
    }

}
