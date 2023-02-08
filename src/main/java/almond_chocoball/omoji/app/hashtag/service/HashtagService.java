package almond_chocoball.omoji.app.hashtag.service;

import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.entity.Post;

import java.util.List;

public interface HashtagService {

    List<HashtagPost> getHashtagPosts(List<String> hashtagNames);

    List<HashtagPost> getEventStyleHashtagPosts(PostRequestDto postRequestDto);

    HashtagPost getLocationHashtagPost(String hashtagName);

    void deleteAllByPosts(List<Post> posts);
}
