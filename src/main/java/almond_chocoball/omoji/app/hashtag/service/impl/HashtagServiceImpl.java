package almond_chocoball.omoji.app.hashtag.service.impl;

import almond_chocoball.omoji.app.hashtag.entity.Hashtag;
import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import almond_chocoball.omoji.app.hashtag.repository.HashtagRepository;
import almond_chocoball.omoji.app.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public List<HashtagPost> getHashtagPosts(List<String> hashtagNames) {
        List<Hashtag> hashtags = hashtagNames.stream()
                .map(hashtagName -> hashtagRepository.findByName(hashtagName)
                        .orElseThrow(()->new NoSuchElementException("존재하는 Hashtag에서 선택해주십시오.")))
                .collect(Collectors.toList());
        return HashtagPost.createHashtagPost(hashtags);
    }
}
