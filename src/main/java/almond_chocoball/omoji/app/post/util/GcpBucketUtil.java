package almond_chocoball.omoji.app.post.util;

import almond_chocoball.omoji.app.post.dto.response.ImgJpgDto;
import almond_chocoball.omoji.app.post.dto.response.ImgResponseDto;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
@Slf4j
public class GcpBucketUtil {

    @Value("${gcp.config.file}")
    private String gcpConfigFile;
    @Value("${gcp.project.id}")
    private String gcpProjectId;
    @Value("${gcp.bucket.id}")
    private String gcpBucketId;
    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    private InputStream ios;
    private StorageOptions options;
    private Storage storage;
    private Bucket bucket;

    @PostConstruct
    protected void init() throws IOException {
        ios = new ClassPathResource(gcpConfigFile).getInputStream();

        options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                .setCredentials(GoogleCredentials.fromStream(ios)).build();

        storage = options.getService();
        bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
    }

    public ImgResponseDto uploadFile(MultipartFile file, String originalName) throws Exception {
        UUID uuid = UUID.randomUUID();
        String fileName = originalName.substring(0, originalName.lastIndexOf("."));
        StringBuilder savedFileName = new StringBuilder(fileName + "-" + uuid);

        ImgJpgDto imgJpgDto = convertToJpg(file, originalName, savedFileName);

        Blob blob = bucket.create(gcpDirectoryName + "/" + savedFileName,
                imgJpgDto.getFileData(), imgJpgDto.getContentType());

        if(blob != null){
            return new ImgResponseDto(blob.getName(), blob.getMediaLink());
        }

        return null;
    }


    private File multipartFileToFile(MultipartFile file) throws Exception {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private ImgJpgDto convertToJpg(MultipartFile file, String originalName, StringBuilder savedFileName) throws Exception {
        String extension = originalName.substring(originalName.lastIndexOf("."));
        Path path;
        String contentType;

        if (!(extension.equals(".jpg") || extension.equals(".gif"))) {
            //jpg로 압축
            savedFileName.append(".jpg");
            File afterFile = new File(savedFileName.toString());

            BufferedImage beforeImg = ImageIO.read(multipartFileToFile(file));
            BufferedImage afterImg = new BufferedImage(beforeImg.getWidth(), beforeImg.getHeight(), BufferedImage.TYPE_INT_RGB);

            afterImg.createGraphics().drawImage(beforeImg, 0, 0, Color.white, null);
            ImageIO.write(afterImg, "jpg", afterFile);

            path = afterFile.toPath();
            contentType = Files.probeContentType(path);
            return new ImgJpgDto(Files.readAllBytes(path), contentType);

        } else {
            savedFileName.append(extension);
            path = new File(originalName).toPath();
            contentType = Files.probeContentType(path);
            return new ImgJpgDto(file.getBytes(), contentType);
        }
    }
}
