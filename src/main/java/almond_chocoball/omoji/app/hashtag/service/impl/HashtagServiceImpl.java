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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public List<HashtagPost> getEventStyleHashtagPosts(PostRequestDto postRequestDto) {
        List<Hashtag> events = hashtagRepository.findHashtagsWithParentIdAndNames(
                HashtagEnum.EVENT.getValue(),
                postRequestDto.getEvents().stream().collect(Collectors.toList()));
        List<Hashtag> styles = hashtagRepository.findHashtagsWithParentIdAndNames(
                HashtagEnum.STYLE.getValue(),
                postRequestDto.getEvents().stream().collect(Collectors.toList()));

        events.addAll(styles);

        return HashtagPost.createHashtagPosts(events);
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteAllByPosts(List<Post> posts) {
        hashtagRepository.deleteAllByPosts(posts);
    }

}
