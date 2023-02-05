package almond_chocoball.omoji.app.hashtag.repository;

import almond_chocoball.omoji.app.hashtag.entity.Hashtag;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByName(String hashtagName);

    @Modifying(clearAutomatically=true, flushAutomatically=true) //bulk update 후 영속성 컨텍스트 비움
    @Query("DELETE FROM HashtagPost hp WHERE hp.post=:posts")
    void deleteAllByPost(@Param("post") Post post);

    @Modifying(clearAutomatically=true, flushAutomatically=true) //bulk update 후 영속성 컨텍스트 비움
    @Query("DELETE FROM HashtagPost hp WHERE hp.post in :posts")
    void deleteAllByPosts(@Param("posts") List<Post> posts);

}
