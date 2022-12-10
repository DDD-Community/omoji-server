package almond_chocoball.omoji.app.post.repository;

import almond_chocoball.omoji.app.post.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    List<String> findUrlByPostIdOrderByIdAsc(Long postId);
}
