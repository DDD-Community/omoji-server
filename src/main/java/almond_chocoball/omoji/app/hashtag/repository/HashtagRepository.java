package almond_chocoball.omoji.app.hashtag.repository;

import almond_chocoball.omoji.app.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByName(String hashtagName);
}
