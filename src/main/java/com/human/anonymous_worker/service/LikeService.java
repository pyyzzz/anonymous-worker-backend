package com.human.anonymous_worker.service;

import com.human.anonymous_worker.dto.response.LikeResDto;
import com.human.anonymous_worker.entity.Like;
import com.human.anonymous_worker.entity.Post;
import com.human.anonymous_worker.entity.User;
import com.human.anonymous_worker.repository.LikeRepository;
import com.human.anonymous_worker.repository.PostRepository;
import com.human.anonymous_worker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 좋아요 토글
     * - 좋아요 안 한 상태 → INSERT + post.likeCount + 1 → liked: true 반환
     * - 이미 좋아요 한 상태 → DELETE + post.likeCount - 1 → liked: false 반환
     * <p>
     * TODO: 아래 흐름을 참고해서 구현하세요.
     */
    public LikeResDto toggleLike(Long postId, Long userId) {
        // TODO: 1. postRepository.findById() 로 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        // TODO: 2. userRepository.findById() 로 유저 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        // TODO: 3. likeRepository.findByUserUserIdAndPostPostId() 로 기존 좋아요 여부 확인
        Optional<Like> existingLike = likeRepository.findByUserUserIdAndPostPostId(userId, postId);

        boolean liked;

        if (existingLike.isEmpty()) {
            // 4-a. 좋아요 추가
            Like like = Like.builder()
                    .user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
            post.setLikeCount(post.getLikeCount() + 1);
            liked = true;
        } else {
            // 4-b. 좋아요 취소
            likeRepository.delete(existingLike.get());
            post.setLikeCount(post.getLikeCount() - 1);
            liked = false;
        }
        // TODO: 4-a. 기존 좋아요 없으면 → Like 엔티티 생성 및 저장 + post.likeCount + 1
        // TODO: 4-b. 기존 좋아요 있으면 → likeRepository.delete() + post.likeCount - 1
        // TODO: 5. postRepository.save() 로 likeCount 업데이트
        postRepository.save(post);
        // TODO: 6. LikeResDto.builder().liked(?).likeCount(?).build() 반환
        return LikeResDto.builder()
                .liked(liked)
                .likeCount(post.getLikeCount())
                .build();
    }

    /**
     * 특정 게시글에 특정 유저가 좋아요 했는지 확인
     * TODO: likeRepository.findByUserUserIdAndPostPostId() 사용
     */
    @Transactional(readOnly = true)
    public boolean isLiked(Long postId, Long userId) {
        // TODO: 좋아요 했으면 true, 안 했으면 false 반환
        return likeRepository.findByUserUserIdAndPostPostId(userId, postId).isPresent();
    }
}
