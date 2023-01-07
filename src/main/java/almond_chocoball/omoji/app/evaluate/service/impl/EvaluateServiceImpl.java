package almond_chocoball.omoji.app.evaluate.service.impl;

import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.entity.Evaluate;
import almond_chocoball.omoji.app.evaluate.repository.EvaluateRepository;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import almond_chocoball.omoji.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
@Service
@Transactional
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {

    private final EvaluateRepository evaluateRepository;
    private final PostRepository postRepository;

    @Override
    public SimpleSuccessResponse insertEvaluate(Member member, EvaluateRequestDto evaluateRequestDto) {
        Post post = postRepository.findById(evaluateRequestDto.getPostId())
                .orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다.");});
        Evaluate evaluate = evaluateRequestDto.toEvaluate(member, post);
        Evaluate resultEvaluate = evaluateRepository.save(evaluate);

        return new SimpleSuccessResponse(
                resultEvaluate.getId()
        );
    }

    @Override
    public ResponseEntity removeEvaluate(Long evaluateId) {
        evaluateRepository.deleteById(evaluateId);
        return ResponseEntity.ok("제거되었습니다.");
    }
}
