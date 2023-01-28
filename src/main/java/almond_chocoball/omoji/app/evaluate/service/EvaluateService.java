package almond_chocoball.omoji.app.evaluate.service;

import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluatePostResponseDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EvaluateService {
    EvaluateResponseDto insertEvaluate(Member member, EvaluateRequestDto evaluateRequestDto);
    ResponseEntity removeEvaluate(Long id);
    List<EvaluatePostResponseDto> getEvaulateIdByMemberId(Member member);
}
