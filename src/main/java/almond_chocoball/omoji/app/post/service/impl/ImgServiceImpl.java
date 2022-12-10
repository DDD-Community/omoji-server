package almond_chocoball.omoji.app.post.service.impl;

import almond_chocoball.omoji.app.post.dto.response.ImgDto;
import almond_chocoball.omoji.app.post.entity.Img;
import almond_chocoball.omoji.app.post.repository.ImgRepository;
import almond_chocoball.omoji.app.post.service.ImgService;
import almond_chocoball.omoji.app.post.util.GcpBucketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService {

    private final ImgRepository imgRepository;
    private final GcpBucketUtil gcpBucketUtil;

    @Override
    public void uploadImg(Img img, MultipartFile imgFile) throws Exception {
        String originalName = imgFile.getOriginalFilename();
        if (originalName == null) {
            throw new RuntimeException("원본 파일명이 null입니다.");
        }
        //파일 업로드
        try {
            ImgDto imgDto = gcpBucketUtil.uploadFile(imgFile, originalName);
            if (imgDto != null) {
                img.updateImg(originalName, imgDto.getName(), imgDto.getUrl());
                imgRepository.save(img);
            }
        } catch (Exception e) {
            throw new Exception("이미지 업로드에 실패했습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getImgUrls(Long postId) {
        return imgRepository.findUrlByPostIdOrderByIdAsc(postId);
    }

}
