package almond_chocoball.omoji.app.evaluate.service.impl;

import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.evaluate.entity.Evaluate;
import almond_chocoball.omoji.app.evaluate.repository.EvaluateRepository;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {

    private final EvaluateRepository evaluateRepository;

    @Override
    public EvaluateResponseDto insertEvaluate(EvaluateRequestDto evaluateRequestDto) {
        Evaluate evaluate = evaluateRequestDto.toEvaluate();
        Evaluate resultEvaluate = evaluateRepository.save(evaluate);

        return new EvaluateResponseDto(
                resultEvaluate.getId(),
                resultEvaluate.getMember(),
                resultEvaluate.getPost(),
                resultEvaluate.getEvaluateEnum()
        );
    }

    @Override
    public ResponseEntity removeEvaluate(Long evaluateId) {
        evaluateRepository.deleteById(evaluateId);
        return ResponseEntity.ok("제거되었습니다.");
    }
}
