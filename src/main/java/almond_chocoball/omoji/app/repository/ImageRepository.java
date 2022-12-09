package almond_chocoball.omoji.app.repository;

import almond_chocoball.omoji.app.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<PostEntity,Long> {
}
