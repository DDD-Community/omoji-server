package almond_chocoball.omoji.app.post.entity;

import almond_chocoball.omoji.app.common.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Img extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String originalName; //원본 이미지 파일명

    private String url; //이미지 조회 경로

    private Boolean represent; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public void updateImg(String originalName, String name, String url) {
        this.originalName = originalName;
        this.name = name;
        this.url = url;
    }
}
