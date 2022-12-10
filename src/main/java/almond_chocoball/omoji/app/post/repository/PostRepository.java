package almond_chocoball.omoji.app.post.repository;

import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}
