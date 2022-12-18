package almond_chocoball.omoji;

import almond_chocoball.omoji.initialize.CreateJSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class OmojiApplication {
	@Value("${GCP_ACCOUNT:default}")
	private String gcpData;

	@PostConstruct
	public void started() {
		if(!gcpData.equals("default")){
			CreateJSON gcpJson = new CreateJSON("omoji-server-account.json",gcpData);
			File file = new File(gcpJson.setPathToResource().toString());
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(gcpData);
				System.out.println("Create GCP Cloud credential file\n"+gcpJson.setPathToResource().toString());
				System.out.println(gcpData);
			} catch (IOException e) {
				System.out.println("Failed create GCP cloud credential file");
				e.printStackTrace();
			}
		}
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
	public static void main(String[] args) {
		SpringApplication.run(OmojiApplication.class, args);
	}

}
