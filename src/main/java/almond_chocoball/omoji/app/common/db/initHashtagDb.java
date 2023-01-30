package almond_chocoball.omoji.app.common.db;

import almond_chocoball.omoji.app.hashtag.entity.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class initHashtagDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.hashtagEventInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void hashtagEventInit() {
            Hashtag child1 = Hashtag.builder().name("결혼식").build();
            Hashtag child2 = Hashtag.builder().name("여행").build();
            Hashtag child3 = Hashtag.builder().name("휴가").build();
            Hashtag child4 = Hashtag.builder().name("데이트").build();
            Hashtag child5 = Hashtag.builder().name("학교").build();
            Hashtag child6 = Hashtag.builder().name("출근").build();
            Hashtag child7 = Hashtag.builder().name("데일리").build();

            Hashtag parent1 = Hashtag.createHashtag("상황",
                    child1, child2, child3, child4, child5, child6, child7);
            Hashtag parent2 = Hashtag.createHashtag("장소");

            em.persist(parent1);
            em.persist(parent2);
        }

    }
}
