package almond_chocoball.omoji.app.post.service.impl;

import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.evaluate.enums.EvaluateEnum;
import almond_chocoball.omoji.app.evaluate.service.EvaluateService;
import almond_chocoball.omoji.app.hashtag.entity.HashtagPost;
import almond_chocoball.omoji.app.hashtag.service.HashtagService;
import almond_chocoball.omoji.app.img.service.ImgService;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import almond_chocoball.omoji.app.post.dto.response.MyPostPagingResponseDto;
import almond_chocoball.omoji.app.post.dto.response.PostsResponseDto;
import almond_chocoball.omoji.app.post.entity.Post;
import almond_chocoball.omoji.app.post.repository.PostRepository;
import almond_chocoball.omoji.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private final HashtagService hashtagService;
    private final EvaluateService evaluateService;

    @Override
    public Post getPostById(Long id){
        return postRepository.findById(id).orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다."); });
    }

    @Override
    public SimpleSuccessResponse uploadPost(Member member, PostRequestDto postRequestDto,
                                            List<MultipartFile> imgFileList) {
        checkImgsLen(imgFileList); //이미지 개수 확인
        checkContentType(imgFileList); //파일 타입 확인

        List<HashtagPost> hashtagPosts = new ArrayList<>();

        List<HashtagPost> eventStyles = hashtagService.getEventStyleHashtagPosts(postRequestDto);
        HashtagPost location = hashtagService.getLocationHashtagPost(postRequestDto.getLocation());

        hashtagPosts.addAll(eventStyles);
        hashtagPosts.add(location);

        Post post = postRequestDto.toPost(member, hashtagPosts);

        Long id = postRepository.save(post).getId(); //post등록

        imgFileList.forEach(imgFile -> { //img등록
            imgService.uploadImg(post, imgFile);
        });

        return new SimpleSuccessResponse(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DetailPostResponseDto getPost(Member member, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다."); });

        DetailPostResponseDto detailPostResponseDto = DetailPostResponseDto.of(post);
        detailPostResponseDto.setLikeCount(evaluateService.countRowByPost(post, EvaluateEnum.LIKE));
        detailPostResponseDto.setDislikeCount(evaluateService.countRowByPost(post, EvaluateEnum.DISLIKE));
        List<String> imgUrls = imgService.getImgUrls(post);

        if (post.getMember().getId() == member.getId()) {
            detailPostResponseDto.setIsOwner(true);
        }
        detailPostResponseDto.setImgs(imgUrls);

        return detailPostResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponseDto<List<MyPostPagingResponseDto>> getMemberPostsWithPaging(Member member,
                                                                                int page, int size) {
        Sort sort = sortByCreatedAt();
        Page<Post> allPosts = postRepository.findAllByMember(member, PageRequest.of(page, size, sort));
        List<MyPostPagingResponseDto> myPostPagingResponseDtoList = allPosts.getContent().stream().map(post -> {
            MyPostPagingResponseDto myPostPagingResponseDto = MyPostPagingResponseDto.of(post);
            myPostPagingResponseDto.setLikeCount(evaluateService.countRowByPost(post, EvaluateEnum.LIKE));
            myPostPagingResponseDto.setDislikeCount(evaluateService.countRowByPost(post, EvaluateEnum.DISLIKE));
            myPostPagingResponseDto.setImgs(imgService.getImgUrls(post));
            return myPostPagingResponseDto;
        }).collect(Collectors.toList());
        return new PostsResponseDto(myPostPagingResponseDtoList);
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponseDto<List<MyPostPagingResponseDto>> getMemberPosts(Member member) {
        Sort sort = sortByCreatedAt();
        List<Post> allPosts = postRepository.findAllByMember(member, sort);
        List<MyPostPagingResponseDto> myPostPagingResponseDtoList = allPosts.stream().map(post -> {
            MyPostPagingResponseDto myPostPagingResponseDto = MyPostPagingResponseDto.of(post);
            myPostPagingResponseDto.setLikeCount(evaluateService.countRowByPost(post, EvaluateEnum.LIKE));
            myPostPagingResponseDto.setDislikeCount(evaluateService.countRowByPost(post, EvaluateEnum.DISLIKE));
            myPostPagingResponseDto.setImgs(imgService.getImgUrls(post));
            return myPostPagingResponseDto;
        }).collect(Collectors.toList());
        return new PostsResponseDto(myPostPagingResponseDtoList);
    }

    @Override
    public SimpleSuccessResponse removeMyPost(Member member, Long id) {
        Post post = postRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다."); });
        imgService.deleteImgs(post);
        postRepository.delete(post);
        return new SimpleSuccessResponse(id);
    }

    @Override
    public void removeMyAllPosts(Member member) { //회원 탈퇴 시 모든 글 삭제
        List<Post> posts = postRepository.findAllByMember(member);
        imgService.deleteImgsByPosts(posts);
        hashtagService.deleteAllByPosts(posts);
        postRepository.deleteAllByMember(member);
    }

    @Override
    public SimpleSuccessResponse updatePost(Member member, PostRequestDto postRequestDto,
                                            List<MultipartFile> imgFileList) {
        checkImgsLen(imgFileList);
        checkContentType(imgFileList);

        Post findPost = postRepository.findByIdAndMember(postRequestDto.getId(), member)
                .orElseThrow(() -> { throw new NoSuchElementException("해당 포스트를 찾을 수 없습니다.");});

        List<HashtagPost> hashtagPosts = hashtagService.getHashtagPosts(postRequestDto.getEvents());

        Post requestPost = postRequestDto.toPost(member, hashtagPosts);
        findPost.updatePost(requestPost); //변경 감지

        imgService.updateImg(findPost, imgFileList);

        return new SimpleSuccessResponse(findPost.getId());
    }


    private Sort sortByCreatedAt() {
        return Sort.by(Sort.Direction.DESC, "createdAt");
    }

    private void checkImgsLen(List<MultipartFile> imgFileList) {
        if (imgFileList.get(0).isEmpty()){
            throw new RuntimeException("최소 1 장의 사진을 첨부해야 합니다.");
        }
        if (imgFileList.size() > 5){
            throw new RuntimeException("최대 5 장까지 첨부 가능합니다.");
        }
    }

    private void checkContentType(List<MultipartFile> imgFileList) {
        for (MultipartFile multipartFile : imgFileList) {
            if (!Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
                throw new RuntimeException("이미지 파일만 업로드 가능합니다.");
            }
        }
    }

}
