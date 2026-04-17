package com.human.anonymous_worker.repository;

import com.human.anonymous_worker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostPostIdOrderByCreatedAtAsc(Long postId);

    // 게시글 별 댓글 수 조회
    long countByPostPostId(Long postId);

    // 게시글 삭제 시 연관 댓글 전체 삭제
    void deleteByPostPostId(Long postId);
}
