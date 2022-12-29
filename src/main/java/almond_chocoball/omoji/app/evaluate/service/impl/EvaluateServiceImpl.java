package almond_chocoball.omoji.app.evaluate.service.impl;

import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.evaluate.entity.EvaluateEntity;
import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.evaluate.repository.EvaluateRepository;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {

    private final EvaluateRepository evaluateRepository;

    public EvaluateEntity setPostByEnum(EvaluateEntity evaluateEntity, EvaluateEnum postReactionStatusEnum, Long point){
        Long disLikeCount = evaluateEntity.getDislikeCount();
        Long likeCount = evaluateEntity.getLikeCount();
        Long passCount = evaluateEntity.getLikeCount();

        switch (postReactionStatusEnum){
            case LIKE:
                evaluateEntity.setLikeCount(likeCount+point);
                break;
            case DISLIKE:
                evaluateEntity.setDislikeCount(disLikeCount+point);
                break;
            default:
                evaluateEntity.setPassCount(passCount+point);
        }

        return evaluateEntity;
    }

    @Override
    public EvaluateResponseDto setEvaluate(Long id, EvaluateEnum evaluateEum, Long point) {
        EvaluateEntity evaluateEntity = evaluateRepository.findById(id)
                .orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다."); });
        EvaluateEntity resultEvaluate = evaluateRepository.save(setPostByEnum(evaluateEntity,evaluateEum,point));

        return new EvaluateResponseDto(
                resultEvaluate.getId(),
                resultEvaluate.getLikeCount(),
                resultEvaluate.getDislikeCount(),
                resultEvaluate.getPassCount()
        );
    }

}
