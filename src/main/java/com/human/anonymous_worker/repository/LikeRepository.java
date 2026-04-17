package com.human.anonymous_worker.repository;

import com.human.anonymous_worker.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    // 특정 유저가 특정 게시글에 좋아요 했는지 확인
    Optional<Like> findByUserUserIdAndPostPostId(Long userId, Long postId);
    // 특정 게시글의 좋아요 수
    long countByPostPostId(Long postId);
}
