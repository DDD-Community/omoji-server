package almond_chocoball.omoji.app.evaluate.service;

import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;

public interface EvaluateService {
    EvaluateResponseDto setEvaluate(Long id, EvaluateEnum postReactionStatusEnum, Long point);
}
