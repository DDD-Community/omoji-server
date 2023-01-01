package almond_chocoball.omoji.app.post.repository;

import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByMember(Member member, Pageable pageable);

    Page<Post> findAllByMemberNot(Member member, Pageable pageable);
}
