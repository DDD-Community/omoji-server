package almond_chocoball.omoji.app.member.entity;

import almond_chocoball.omoji.app.auth.enums.Role;
import almond_chocoball.omoji.app.auth.enums.Social;
import almond_chocoball.omoji.app.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

//    @Column(nullable = false)
//    private Gender gender; //F/M/U
//
//    @Column(nullable = false)
//    private String birthyear; //2000

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Social social;

    @Column
    private String refreshToken;

}
