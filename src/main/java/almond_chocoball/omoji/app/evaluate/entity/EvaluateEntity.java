package almond_chocoball.omoji.app.evaluate.entity;

import almond_chocoball.omoji.app.common.entity.BaseTimeEntity;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class EvaluateEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Long dislikeCount;

    @Column(nullable = false)
    private Long passCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateEvaluate(Long likeCount, Long dislikeCount, Long passCount){
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.passCount = passCount;
    }
}
