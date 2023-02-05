package almond_chocoball.omoji.app.post.repository;

import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByMember(Member member, Pageable pageable);
    List<Post> findAllByMember(Member member);

    Page<Post> findAllByMemberNot(Member member, Pageable pageable);

    Optional<Post> findByIdAndMember(Long id, Member member);

    @Modifying(clearAutomatically=true, flushAutomatically=true) //bulk update 후 영속성 컨텍스트 비움
    @Query("UPDATE Post p SET p.isDeleted=true WHERE p.member=:member")
    void deleteAllByMember(@Param("member") Member member);
}
