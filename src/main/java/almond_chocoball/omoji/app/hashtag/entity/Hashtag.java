package almond_chocoball.omoji.app.hashtag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Hashtag parent; //주인

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, orphanRemoval = true) //자식 list추가 후 저장 시 해당 entity도 persist
    @ToString.Exclude
    @JsonIgnore
    private List<Hashtag> child = new ArrayList<>(); //종속

    //==연관관계 메서드==양방향일 때//
    public void addChildHashtag(Hashtag child) {
        this.child.add(child);
        child.setParent(this);
    }

    public static Hashtag createHashtag(String name, Hashtag... child){
        Hashtag hashtag = new Hashtag();
        hashtag.setName(name);
        Arrays.stream(child).forEach(c -> hashtag.addChildHashtag(c));
        return hashtag;
    }

}
