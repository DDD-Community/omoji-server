package almond_chocoball.omoji.app.comment.service.impl;

import almond_chocoball.omoji.app.comment.dto.request.CommentRequestDto;
import almond_chocoball.omoji.app.comment.dto.response.CommentResponseDto;
import almond_chocoball.omoji.app.comment.entity.Comment;
import almond_chocoball.omoji.app.comment.repository.CommentRepository;
import almond_chocoball.omoji.app.comment.service.CommentService;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.evaluate.dto.request.EvaluateRequestDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluatePostResponseDto;
import almond_chocoball.omoji.app.evaluate.dto.response.EvaluateResponseDto;
import almond_chocoball.omoji.app.evaluate.entity.Evaluate;
import almond_chocoball.omoji.app.img.service.ImgService;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;
import almond_chocoball.omoji.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment getComment(Long id){
        return commentRepository.findById(id).orElseThrow(()->{throw new NoSuchElementException("해당 댓글을 찾을 수 없습니다.");});
    }

    @Override
    public CommentResponseDto getCommentById(Long id){
        Comment commentResult = commentRepository.findById(id).orElseThrow(() -> {throw new NoSuchElementException("해당 댓글을 찾을 수 없습니다.");});
        return new CommentResponseDto(
                commentResult.getId(),
                commentResult.getComment(),
                commentResult.getPost()
        );
    }

    @Override
    public List<CommentResponseDto> getCommentsByPost(Post post){
        List<Comment> commentResult = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<CommentResponseDto>();
        for (Comment comment: commentResult){
            commentResponseDtoList.add(
                    new CommentResponseDto(
                            comment.getId(),
                            comment.getComment(),
                            comment.getPost()
                    )
            );
        }
        return commentResponseDtoList;
    }

    @Override
    public SimpleSuccessResponse postCommentByPostId(Member member, Post post, CommentRequestDto commentRequestDto){
        Comment comment = new Comment();
        comment.setComment(commentRequestDto.getComment());
        comment.setMember(member);
        comment.setPost(post);
        Comment resultComment=commentRepository.save(comment);
        return new SimpleSuccessResponse(resultComment.getId());
    }

    @Override
    public SimpleSuccessResponse putCommentByPostId(Member member, Post post, Comment comment, CommentRequestDto commentRequestDto){
        if(!member.getId().equals(comment.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        comment.setComment(commentRequestDto.getComment());
        comment.setMember(member);
        comment.setPost(post);
        Comment resultComment=commentRepository.save(comment);
        return new SimpleSuccessResponse(resultComment.getId());
    }

    @Override
    public SimpleSuccessResponse deleteCommentById(Member member, Comment comment){
        if(!member.getId().equals(comment.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        commentRepository.delete(comment);
        return new SimpleSuccessResponse(comment.getId());
    }
}
