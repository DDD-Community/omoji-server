package almond_chocoball.omoji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing //자동 createAt, updatedAt 활성화
//@EnableWebSecurity
public class OmojiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmojiApplication.class, args);
	}

}
