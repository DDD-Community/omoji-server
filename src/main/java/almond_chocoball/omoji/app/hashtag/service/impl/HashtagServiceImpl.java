package almond_chocoball.omoji.app.hashtag.service.impl;

import almond_chocoball.omoji.app.hashtag.entity.Hashtag;
import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import almond_chocoball.omoji.app.hashtag.repository.HashtagRepository;
import almond_chocoball.omoji.app.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public List<HashtagPost> getHashtagPosts(List<Long> hashtagIds) {
        List<Hashtag> hashtags = hashtagRepository.findAllById(hashtagIds);
        return HashtagPost.createHashtagPost(hashtags);
    }
}
