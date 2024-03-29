package almond_chocoball.omoji.app.post.repository.dao;

import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDaoImpl {

    private final EntityManager em;

    public List<Post> getAllWithHashtagByMemberNot(Member member) {
        //post<->hashtagPosts 양방향
        //collection 하나에만 fetch join 가능
        //1:m fetch join -> paging 불가
        return em.createQuery(
                "select distinct p from Post p" +
                        " join fetch p.hashtagPosts hp" +
                        " join fetch hp.hashtag h" +
                        " where p.member!=:member", Post.class
        ).setParameter("member", member)
        .getResultList();
    }
}
