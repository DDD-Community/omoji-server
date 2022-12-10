package almond_chocoball.omoji.app.post.util;

import almond_chocoball.omoji.app.post.dto.response.ImgDto;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class GcpBucketUtil {

    @Value("${gcp.config.file}")
    private String gcpConfigFile;
    @Value("${gcp.project.id}")
    private String gcpProjectId;
    @Value("${gcp.bucket.id}")
    private String gcpBucketId;
    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public ImgDto uploadFile(MultipartFile file, String originalName) throws Exception, IOException {
        //byte[] fileData = FileUtils.readFileToByteArray(convertFile(file));
        byte[] fileData = file.getBytes();
        InputStream ios = new ClassPathResource(gcpConfigFile).getInputStream();

        StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                .setCredentials(GoogleCredentials.fromStream(ios)).build();

        Storage storage = options.getService();
        Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());

        UUID uuid = UUID.randomUUID();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String savedFileName = originalName + "-" + uuid + extension;

        Path path = new File(originalName).toPath();
        String contentType = Files.probeContentType(path);

        Blob blob = bucket.create(gcpDirectoryName + "/" + savedFileName, fileData, contentType);

        if(blob != null){
            return new ImgDto(blob.getName(), blob.getMediaLink());
        }

        return null;
    }

    private File convertFile(MultipartFile file) throws Exception {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }
}
