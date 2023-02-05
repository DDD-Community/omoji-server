package almond_chocoball.omoji.app.img.repository;

import almond_chocoball.omoji.app.img.entity.Img;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    @Query("SELECT i.url FROM Img i WHERE i.post=:post")
    List<String> findUrlByPostId(@Param("post") Post post);

    @Query("SELECT i FROM Img i WHERE i.post=:post")
    List<Img> findByPostId(@Param("post") Post post);

    @Modifying(clearAutomatically=true, flushAutomatically=true) //bulk update 후 영속성 컨텍스트 비움
    @Query("UPDATE Img i SET i.isDeleted=true WHERE i.post=:post")
    void deleteAllByPost(@Param("post") Post post);

    @Modifying(clearAutomatically=true, flushAutomatically=true) //bulk update 후 영속성 컨텍스트 비움
    @Query("UPDATE Img i SET i.isDeleted=true WHERE i.post in :posts")
    void deleteAllByPosts(@Param("posts") List<Post> posts);
}
