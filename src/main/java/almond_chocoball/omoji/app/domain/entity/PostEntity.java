package almond_chocoball.omoji.app.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id",nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long number_like= 0L;

    @Column(nullable = false)
    private Long number_dislike = 0L;

    @OneToMany(mappedBy = "post")
    private List<ImageEntity> images = new ArrayList<>();

    @Builder
    public PostEntity(Long number_like,Long number_dislike) {
        this.number_like = number_like;
        this.number_dislike = number_dislike;
    }

    public void addImage(ImageEntity image) {
        this.images.add(image);
    }
}