package almond_chocoball.omoji.app.img.service.impl;

import almond_chocoball.omoji.app.img.dto.response.ImgResponseDto;
import almond_chocoball.omoji.app.img.entity.Img;
import almond_chocoball.omoji.app.post.entity.Post;
import almond_chocoball.omoji.app.img.repository.ImgRepository;
import almond_chocoball.omoji.app.img.service.ImgService;
import almond_chocoball.omoji.app.img.util.GcpBucketUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
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
            ImgResponseDto imgResponseDto = gcpBucketUtil.uploadFile(imgFile, originalName);
            if (imgResponseDto != null) {
                img.updateImg(originalName, imgResponseDto.getName(), imgResponseDto.getUrl());
                imgRepository.save(img);
            }
        } catch (Exception e) {
            throw new Exception("이미지 업로드에 실패했습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getImgUrls(Post post) {
        return imgRepository.findUrlByPostIdOrderByIdAsc(post);
    }

}
