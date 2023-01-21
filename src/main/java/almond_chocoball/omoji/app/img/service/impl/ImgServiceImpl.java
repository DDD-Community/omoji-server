package almond_chocoball.omoji.app.img.service.impl;

import almond_chocoball.omoji.app.img.dto.request.ImgRequestDto;
import almond_chocoball.omoji.app.img.dto.response.ImgResponseDto;
import almond_chocoball.omoji.app.img.entity.Img;
import almond_chocoball.omoji.app.img.repository.ImgRepository;
import almond_chocoball.omoji.app.img.service.ImgService;
import almond_chocoball.omoji.app.img.util.GcpBucketUtil;
import almond_chocoball.omoji.app.post.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService {

    private final ImgRepository imgRepository;
    private final GcpBucketUtil gcpBucketUtil;

    @Override
    public void uploadImg(Post post, MultipartFile imgFile) {
        String originalName = imgFile.getOriginalFilename();
        if (originalName == null) {
            throw new RuntimeException("원본 파일명이 null입니다.");
        }

        ImgRequestDto imgRequestDto = new ImgRequestDto();
        imgRequestDto.setPost(post);

        //파일 업로드
        try {
            ImgResponseDto imgResponseDto = gcpBucketUtil.uploadFile(imgFile, originalName);
            if (imgResponseDto != null) {
                Img img = imgRequestDto.toImg();
                img.updateImg(originalName, imgResponseDto.getName(), imgResponseDto.getUrl());
                imgRepository.save(img);
            }
        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드에 실패했습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getImgUrls(Post post) {
        return imgRepository.findUrlByPostId(post);
    }

    @Override
    public void deleteImgs(Post post) {
        imgRepository.deleteAllByPost(post);
    }


    @Override
    public void updateImg(Post post, List<MultipartFile> imgFileList) {
        List<Img> findImgs = imgRepository.findByPostId(post); //이미 존재하는 이미지
        List<String> reqNameList = imgFileList.stream() //요청으로 들어온 파일명
                .map(img -> img.getOriginalFilename()).collect(Collectors.toList());
        // DB에 존재하면서 요청에도 존재하는 같은 파일 -> 새로 저장할 필요x
        Map<String, Img> dbSame = new HashMap<>();

        findImgs.forEach(findImg -> { //db의 이미지명이 요청 파일명에 없을 때 제거
            String dbFileName = findImg.getOriginalName();
            if (!reqNameList.contains(dbFileName)) imgRepository.delete(findImg);
            else dbSame.put(dbFileName, findImg);
        });
        
        imgFileList.forEach(imgFile -> {
            String reqFileName = imgFile.getOriginalFilename();
            if (!dbSame.keySet().contains(reqFileName)) uploadImg(post, imgFile); //새로 업로드
            else dbSame.get(reqFileName).setUpdatedAt(LocalDateTime.now()); //순서 때문에 시간만 업데이트
        });
    }
}