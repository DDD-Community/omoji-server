package almond_chocoball.omoji.app.post.service.impl;

import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.img.entity.Img;
import almond_chocoball.omoji.app.post.dto.response.PostPagingResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostsResponseDto;
import almond_chocoball.omoji.app.post.entity.Post;
import almond_chocoball.omoji.app.post.repository.PostRepository;
import almond_chocoball.omoji.app.img.service.ImgService;
import almond_chocoball.omoji.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImgService imgService;

    @Override
    public SimpleSuccessResponse uploadPost(PostRequestDto postRequestDto, List<MultipartFile> imgFileList) throws Exception {
        if (imgFileList.get(0).isEmpty()){
            throw new RuntimeException("최소 1 장의 사진을 첨부해야 합니다.");
        }
        if (imgFileList.size() > 5){
            throw new RuntimeException("최대 5 장까지 첨부 가능합니다.");
        }

        Post post = postRequestDto.toPost();

        Long id = postRepository.save(post).getId(); //post등록
        uploadImgs(post, imgFileList); //img등록

        SimpleSuccessResponse simpleSuccessResponse = new SimpleSuccessResponse(id);
        return simpleSuccessResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public DetailPostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다."); });

        DetailPostResponseDto detailPostResponseDto = DetailPostResponseDto.of(post);
        List<String> imgUrls = imgService.getImgUrls(post);

        detailPostResponseDto.setImgs(imgUrls);

        return detailPostResponseDto;
    }


    private void uploadImgs(Post post, List<MultipartFile> imgFileList) throws Exception {
        for (MultipartFile multipartFile : imgFileList) {
            if (!Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
                throw new RuntimeException("이미지 파일만 업로드 가능합니다.");
            }
        }
        for(int i=0;i<imgFileList.size();i++){
            Img img = new Img();
            img.setPost(post);

            if(i == 0)
                img.setRepresent(true); //대표 이미지
            else
                img.setRepresent(false);

            imgService.uploadImg(img, imgFileList.get(i));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponseDto<List<PostPagingResponseDto>> getPostsWithPaging(int page, int size) {
        Sort sort = sortByCreatedAt();
        Page<Post> allPosts = postRepository.findAll(PageRequest.of(page, size, sort));
        List<PostPagingResponseDto> pagingResponseDtoList = allPosts.getContent().stream().map(post -> {
            PostPagingResponseDto pagingResponseDto = PostPagingResponseDto.of(post);
            pagingResponseDto.setImgs(imgService.getImgUrls(post));
            return pagingResponseDto;
        }).collect(Collectors.toList());
        return new PostsResponseDto(pagingResponseDtoList);
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponseDto<List<DetailPostResponseDto>> getMyPostsWithPaging(int page, int size) {
        Sort sort = sortByCreatedAt();
        Page<Post> allPosts = postRepository.findAll(PageRequest.of(page, size, sort));
        List<DetailPostResponseDto> detailPostResponseDtoList = allPosts.getContent().stream().map(post -> {
            DetailPostResponseDto detailPostResponseDto = DetailPostResponseDto.of(post);
            detailPostResponseDto.setImgs(imgService.getImgUrls(post));
            return detailPostResponseDto;
        }).collect(Collectors.toList());
        return new PostsResponseDto(detailPostResponseDtoList);
    }

    private Sort sortByCreatedAt() {
        return Sort.by(Sort.Direction.DESC, "createdAt");
    }
}
