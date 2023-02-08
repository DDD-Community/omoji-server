package almond_chocoball.omoji.app.post.dto.request;

import almond_chocoball.omoji.app.common.utils.CustomObjectMapper;
import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

    private Long id;

    @NotBlank
    @Size(min=1, max=38)
    private String title;

    @Size(max=100)
    private String description;

    private List<String> events = new ArrayList<>(); //상황

    private List<String> styles = new ArrayList<>(); //스타일

    private String location; //장소

    public Post toPost(Member member, List<HashtagPost> hashtagPosts) { //Dto->Entity
        Post post = CustomObjectMapper.to(this, Post.class);
        post.createPost(member, hashtagPosts);
        return post;
    }
}
