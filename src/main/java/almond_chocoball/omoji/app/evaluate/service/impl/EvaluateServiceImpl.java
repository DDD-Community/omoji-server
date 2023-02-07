package almond_chocoball.omoji.app.evaluate.service.impl;

import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluatePostResponseDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.evaluate.entity.Evaluate;
import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.evaluate.repository.EvaluateRepository;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import almond_chocoball.omoji.app.hashtag.dto.response.HashtagResponseDto;
import almond_chocoball.omoji.app.img.service.ImgService;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import almond_chocoball.omoji.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {

    private final EvaluateRepository evaluateRepository;
    private final PostRepository postRepository;
    private final ImgService imgService;

    @Override
    public List<EvaluatePostResponseDto> getEvaulateIdByMemberId(Member member){
        List<Post> posts = evaluateRepository.findAllByMemberId(member.getId()).orElseThrow(() -> { throw new NoSuchElementException("검색할 수 없습니다.");});
        List<EvaluatePostResponseDto> evaluatePostResponseDtoList = new ArrayList<>();
        for(Post post_item : posts){
            EvaluatePostResponseDto evaluatePostResponseDto = new EvaluatePostResponseDto(
                    post_item.getId(),
                    post_item.getTitle(),
                    imgService.getImgUrls(post_item),
                    post_item.getHashtagPosts().stream()
                            .map(hashtagPost -> new HashtagResponseDto(hashtagPost).getName())
                            .collect(Collectors.toList())
                    );
            evaluatePostResponseDtoList.add(evaluatePostResponseDto);
        }
        return evaluatePostResponseDtoList;
    }

    @Override
    public ResponseEntity removeEvaluate(Long evaluateId) {
        evaluateRepository.deleteById(evaluateId);
        return ResponseEntity.ok("제거되었습니다.");
    }

    @Override
    public EvaluateResponseDto insertEvaluate(Member member, EvaluateRequestDto evaluateRequestDto) {
        Post post = postRepository.findById(evaluateRequestDto.getPostId())
                .orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다.");});
        Evaluate evaluate = evaluateRequestDto.toEvaluate(member, post);
        Evaluate resultEvaluate = evaluateRepository.save(evaluate);

        return new EvaluateResponseDto(
                resultEvaluate.getId(),
                resultEvaluate.getPost(),
                resultEvaluate.getEvaluateEnum()
        );
    }

    @Override
    public int countRowByPost(Post post, EvaluateEnum evaluateEnum){
        return evaluateRepository.countEvaluatesByPostAndEvaluateEnum(post,evaluateEnum);
    }
}
