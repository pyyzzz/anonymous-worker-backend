package com.human.anonymous_worker.repository;

import com.human.anonymous_worker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostPostIdOrderByCreatedAtAsc(Long postId);

    // 게시글별 댓글 수 조회 (PostResDto commentCount 집계용)
    long countByPostPostId(Long postId);
}
