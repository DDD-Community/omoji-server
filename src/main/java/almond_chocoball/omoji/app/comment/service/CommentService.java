package almond_chocoball.omoji.app.comment.service;

import almond_chocoball.omoji.app.comment.dto.request.CommentRequestDto;
import almond_chocoball.omoji.app.comment.dto.response.CommentResponseDto;
import almond_chocoball.omoji.app.comment.entity.Comment;
import almond_chocoball.omoji.app.common.dto.SimpleSuccessResponse;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.post.entity.Post;

import java.util.List;

public interface CommentService {
    Comment getComment(Long id);
    CommentResponseDto getCommentById(Long id);
    List<CommentResponseDto> getCommentsByPost(Post post);
    SimpleSuccessResponse postCommentByPostId(Member member, Post post, CommentRequestDto commentRequestDto);
    SimpleSuccessResponse putCommentByPostId(Member member, Post post, Comment comment, CommentRequestDto commentRequestDto);
    SimpleSuccessResponse deleteCommentById(Member member,Comment comment);
}
