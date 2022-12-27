package almond_chocoball.omoji;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableWebSecurity
public class OmojiApplication {
	@Value("${GCP_ACCOUNT:default}")
	private String gcpData;

	@PostConstruct
	public void started() {
		/*System.out.println("hello");
		System.out.println(gcpData);
		if(!gcpData.equals("default")){
			CreateJSON gcpJson = new CreateJSON("omoji-server-account.json",gcpData);
			File file = new File(gcpJson.setPathToResource().toString());
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(gcpData);
				System.out.println("Create GCP Cloud credential file\n");
				System.out.println(file.getPath());
			} catch (IOException e) {
				System.out.println("Failed create GCP cloud credential file");
				e.printStackTrace();
			}
		}*/
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
	public static void main(String[] args) {
		SpringApplication.run(OmojiApplication.class, args);
	}

}
