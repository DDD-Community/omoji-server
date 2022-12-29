package almond_chocoball.omoji.app.evaluate.controller;

import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Evaluate reaction",description = "Post를 평가하는 페이지")
public class EvaluateController {

    private final EvaluateService evaluateService;

    @PostMapping("/reactions")
    @Tag(name = "Evaluate reaction")
    @Operation(summary = "평가하기", description = "Post ID를 입력받고 좋아요(LIKE), 싫어요(DISLIKE), 넘어가기(PASS)를 선택항 값 증가")
    public ResponseEntity<EvaluateResponseDto> increaseLike(EvaluateRequestDto evaluateRequestDto) {
        return ApiResponse.success(evaluateService.setEvaluate(
                evaluateRequestDto.getPostId(),
                evaluateRequestDto.getEvaluateEnum(),
                1L));
    }

    @DeleteMapping("/reactions")
    @Tag(name = "Evaluate reaction")
    @Operation(summary = "평가 제거하기", description = "Post ID를 입력받고 좋아요(LIKE), 싫어요(DISLIKE), 넘어가기(PASS)를 선택하여 값 감소")
    public ResponseEntity<EvaluateResponseDto> increaseDislike(EvaluateRequestDto evaluateRequestDto){
        return ApiResponse.success(evaluateService.setEvaluate(
                evaluateRequestDto.getPostId(),
                evaluateRequestDto.getEvaluateEnum(),
                -1L));
    }

}
