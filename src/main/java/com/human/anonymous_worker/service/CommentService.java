package com.human.anonymous_worker.service;

import com.human.anonymous_worker.dto.request.CommentReqDto;
import com.human.anonymous_worker.dto.response.CommentResDto;
import com.human.anonymous_worker.entity.Comment;
import com.human.anonymous_worker.entity.Post;
import com.human.anonymous_worker.entity.User;
import com.human.anonymous_worker.repository.CommentRepository;
import com.human.anonymous_worker.repository.PostRepository;
import com.human.anonymous_worker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j @Service @RequiredArgsConstructor @Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResDto saveComment(Long postId, CommentReqDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return CommentResDto.from(commentRepository.save(
                Comment.builder().post(post).user(user).content(dto.getContent()).build()));
    }

    @Transactional(readOnly = true)
    public List<CommentResDto> getCommentList(Long postId) {
        return commentRepository.findByPostPostIdOrderByCreatedAtAsc(postId).stream()
                .map(CommentResDto::from).collect(Collectors.toList());
    }

    public CommentResDto updateComment(Long commentId, CommentReqDto dto) {
        Comment comment = findCommentById(commentId);
        if (!comment.getUser().getUserId().equals(dto.getUserId()))
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        comment.setContent(dto.getContent());
        return CommentResDto.from(commentRepository.save(comment));
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = findCommentById(commentId);
        if (!comment.getUser().getUserId().equals(userId))
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        commentRepository.delete(comment);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}
