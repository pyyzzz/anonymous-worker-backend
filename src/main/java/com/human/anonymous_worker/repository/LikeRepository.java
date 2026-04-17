package com.human.anonymous_worker.repository;

import com.human.anonymous_worker.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    // 특정 게시글에 사용자가 좋아요 했는지 확인
    Optional<Like> findByUserUserIdAndPostPostId(Long userId, Long postId);


    long countByPostPostId(Long postId);

    // 게시글 삭제 시 연관 좋아요 전체 삭제
    void deleteByPostPostId(Long postId);
}
