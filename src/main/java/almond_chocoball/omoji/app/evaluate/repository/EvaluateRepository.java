package almond_chocoball.omoji.app.evaluate.repository;

import almond_chocoball.omoji.app.evaluate.entity.Evaluate;
import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvaluateRepository extends JpaRepository<Evaluate, Long> {
    @Query("SELECT p FROM Post p WHERE NOT EXISTS ( SELECT e from Evaluate e where e.member.id = :memberId AND p.id = e.post.id) AND p.member.id <> :memberId ORDER BY p.updatedAt DESC")
    Optional<List<Post>> findAllByMemberId(@Param("memberId") Long memberId);

    int countEvaluatesByPostAndEvaluateEnum(Post post, EvaluateEnum evaluateEnum);
}
