package almond_chocoball.omoji.app.evaluate.service;

import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import org.springframework.http.ResponseEntity;

public interface EvaluateService {
    EvaluateResponseDto insertEvaluate(EvaluateRequestDto evaluateRequestDto);
    ResponseEntity removeEvaluate(Long id);
}
