package almond_chocoball.omoji.app.post.repository;

import almond_chocoball.omoji.app.post.entity.Img;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    @Query("SELECT i.url FROM Img i WHERE i.post=:post")
    List<String> findUrlByPostIdOrderByIdAsc(@Param("post") Post post);
}
