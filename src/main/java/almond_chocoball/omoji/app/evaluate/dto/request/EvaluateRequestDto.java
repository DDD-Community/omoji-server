package almond_chocoball.omoji.app.evaluate.dto.request;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.evaluate.entity.Evaluate;
import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluateRequestDto {
    private Long postId;
    private EvaluateEnum evaluateEnum;

    public Evaluate toEvaluate() {
        Evaluate evaluateEntity = CustomObjectMapper.to(this, Evaluate.class);
        return evaluateEntity;
    }
}

