package almond_chocoball.omoji.app.img.repository;

import almond_chocoball.omoji.app.img.entity.Img;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    @Query("SELECT i.url FROM Img i WHERE i.post=:post")
    List<String> findUrlByPostId(@Param("post") Post post);

    @Query("SELECT i FROM Img i WHERE i.post=:post")
    List<Img> findByPostId(@Param("post") Post post);

    void deleteAllByPost(Post post);
}
