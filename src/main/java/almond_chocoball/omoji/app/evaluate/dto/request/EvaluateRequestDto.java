package almond_chocoball.omoji.app.evaluate.dto.request;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.evaluate.entity.Evaluate;
import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
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

    public Evaluate toEvaluate(Member member, Post post) {
        Evaluate evaluateEntity = CustomObjectMapper.to(this, Evaluate.class);
        evaluateEntity.setMember(member);
        evaluateEntity.setPost(post);
        return evaluateEntity;
    }
}

