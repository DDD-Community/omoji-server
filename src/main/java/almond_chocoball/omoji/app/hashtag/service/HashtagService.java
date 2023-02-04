package almond_chocoball.omoji.app.hashtag.service;

import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;

import java.util.List;

public interface HashtagService {

    List<HashtagPost> getHashtagPosts(List<String> hashtagNames);
}
