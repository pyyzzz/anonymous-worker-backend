package com.human.anonymous_worker.service;

import com.human.anonymous_worker.dto.request.PostReqDto;
import com.human.anonymous_worker.dto.response.PostResDto;
import com.human.anonymous_worker.entity.Post;
import com.human.anonymous_worker.entity.User;
import com.human.anonymous_worker.entity.enums.PostCategory;
import com.human.anonymous_worker.repository.CommentRepository;
import com.human.anonymous_worker.repository.LikeRepository;
import com.human.anonymous_worker.repository.PostRepository;
import com.human.anonymous_worker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;       // 추가

    public PostResDto savePost(PostReqDto dto) {
        User user = findUserById(dto.getUserId());
        Post post = Post.builder()
                .user(user).title(dto.getTitle()).content(dto.getContent())
                .category(dto.getCategory() != null ? dto.getCategory() : PostCategory.FREE)
                .build();
        return PostResDto.from(postRepository.save(post), commentRepository);
    }

    @Transactional(readOnly = true)
    public List<PostResDto> getPostList() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(p -> PostResDto.from(p, commentRepository))
                .collect(Collectors.toList());
    }

    public PostResDto getPost(Long postId) {
        Post post = findPostById(postId);
        post.setViewCount(post.getViewCount() + 1);
        return PostResDto.from(postRepository.save(post), commentRepository);
    }

    @Transactional(readOnly = true)
    public List<PostResDto> getPostListByCategory(PostCategory category) {
        return postRepository.findByCategoryOrderByCreatedAtDesc(category).stream()
                .map(p -> PostResDto.from(p, commentRepository))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResDto> searchPost(String keyword) {
        return postRepository.findByTitleContainingOrderByCreatedAtDesc(keyword).stream()
                .map(p -> PostResDto.from(p, commentRepository))
                .collect(Collectors.toList());
    }

    public PostResDto updatePost(Long postId, PostReqDto dto) {
        Post post = findPostById(postId);
        if (!post.getUser().getUserId().equals(dto.getUserId()))
            throw new IllegalArgumentException("게시글 수정 권한이 없습니다.");
        if (dto.getTitle() != null) post.setTitle(dto.getTitle());
        if (dto.getContent() != null) post.setContent(dto.getContent());
        if (dto.getCategory() != null) post.setCategory(dto.getCategory());
        return PostResDto.from(postRepository.save(post), commentRepository);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = findPostById(postId);
        User user = findUserById(userId);

        // 관리자 또는 게시글 작성자만 삭제 가능
        boolean isAdmin = user.isAdmin();
        boolean isWriter = post.getUser().getUserId().equals(userId);

        if (!isAdmin && !isWriter)
            throw new IllegalArgumentException("게시글 삭제 권한이 없습니다.");

        // 연관 데이터 먼저 삭제 (FK 제약 위반 방지)
        likeRepository.deleteByPostPostId(postId);
        commentRepository.deleteByPostPostId(postId);

        postRepository.delete(post);
        log.info("게시글 삭제 완료: postId={}, deletedBy={}, isAdmin={}", postId, userId, isAdmin);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. postId=" + postId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. userId=" + userId));
    }
}
