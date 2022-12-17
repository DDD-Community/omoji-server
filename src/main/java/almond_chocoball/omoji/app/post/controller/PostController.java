package almond_chocoball.omoji.app.post.controller;

import almond_chocoball.omoji.app.common.dto.ApiResponse;
import almond_chocoball.omoji.app.post.dto.request.PostRequestDto;
import almond_chocoball.omoji.app.post.dto.response.DetailPostResponseDto;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //img까지 첨부 - formData의 경우 @RequsetBody적지 말고, Dto에 반드시 Setter열어놔야함
    public ResponseEntity<SimpleSuccessResponse> uploadPost(@Valid PostRequestDto postRequestDto,
                                                            @RequestParam("imgs") List<MultipartFile> imgFileList) throws Exception {
        return ApiResponse.created(postService.uploadPost(postRequestDto, imgFileList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailPostResponseDto> getItem(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.getPost(id));
    }

}
