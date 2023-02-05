package almond_chocoball.omoji.app.img.entity;

import almond_chocoball.omoji.app.common.entity.BaseTimeEntity;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false") //삭제하지 않은 img랑만 join 가능
@SQLDelete(sql = "UPDATE img SET is_deleted = true WHERE id = ?")
public class Img extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String originalName; //원본 이미지 파일명

    @Column(length = 500)
    private String url; //이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    public void updateImg(String originalName, String name, String url) {
        this.originalName = originalName;
        this.name = name;
        this.url = url;
    }
}
