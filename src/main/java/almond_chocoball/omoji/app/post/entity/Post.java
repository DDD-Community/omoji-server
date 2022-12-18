package almond_chocoball.omoji.app.post.entity;

import almond_chocoball.omoji.app.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long likeCount = 0L;

    @Column(nullable = false)
    private Long dislikeCount = 0L;

}
