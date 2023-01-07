package almond_chocoball.omoji.app.evaluate.service;

import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.member.entity.Member;
import org.springframework.http.ResponseEntity;

public interface EvaluateService {
    EvaluateResponseDto insertEvaluate(Member member, EvaluateRequestDto evaluateRequestDto);
    ResponseEntity removeEvaluate(Long id);
}
