package almond_chocoball.omoji.app.hashtag.service.impl;

import almond_chocoball.omoji.app.hashtag.entity.Hashtag;
import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import almond_chocoball.omoji.app.hashtag.enums.HashtagEnum;
import almond_chocoball.omoji.app.hashtag.repository.HashtagRepository;
import almond_chocoball.omoji.app.hashtag.service.HashtagService;
import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return HashtagPost.createHashtagPosts(hashtags);
    }

    @Override
    public List<HashtagPost> getEventStyleHashtagPosts(PostRequestDto postRequestDto) {
        Stream<Hashtag> events = postRequestDto.getEvents().stream()
                .map(hashtagName -> hashtagRepository.findByNameAndParentId(hashtagName, HashtagEnum.EVENT.getValue())
                        .orElseThrow(() -> new NoSuchElementException("존재하는 Hashtag에서 선택해주십시오.")));

        Stream<Hashtag> styles = postRequestDto.getStyles().stream()
                .map(hashtagName -> hashtagRepository.findByNameAndParentId(hashtagName, HashtagEnum.STYLE.getValue())
                        .orElseThrow(()->new NoSuchElementException("존재하는 Hashtag에서 선택해주십시오.")));

        List<Hashtag> hashtags = Stream.concat(events, styles).collect(Collectors.toList());

        return HashtagPost.createHashtagPosts(hashtags);
    }

    @Override
    public HashtagPost getLocationHashtagPost(String hashtagName) {
        Hashtag hashtag = hashtagRepository.findByNameAndParentId(hashtagName, HashtagEnum.LOCATION.getValue())
                .orElseGet(() -> {
                    Hashtag newLocation = Hashtag.createHashtagWithParent(
                            hashtagName,
                            hashtagRepository.findById(HashtagEnum.LOCATION.getValue()).get());
                    hashtagRepository.save(newLocation);
                    return newLocation;
                }); //존재하지 않을 때 장소 추가

        return HashtagPost.createHashtagPost(hashtag);
    }


    @Override
    public void deleteAllByPosts(List<Post> posts) {
        hashtagRepository.deleteAllByPosts(posts);
    }


}
