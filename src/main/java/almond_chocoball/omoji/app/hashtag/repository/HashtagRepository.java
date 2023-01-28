package almond_chocoball.omoji.app.hashtag.repository;

import almond_chocoball.omoji.app.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
