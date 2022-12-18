package almond_chocoball.omoji.app.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImgJpgDto {
    private byte[] fileData;
    private String contentType;
}
