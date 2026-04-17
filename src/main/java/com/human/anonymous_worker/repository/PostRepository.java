package com.human.anonymous_worker.repository;

import com.human.anonymous_worker.entity.Post;
import com.human.anonymous_worker.entity.enums.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByCategoryOrderByCreatedAtDesc(PostCategory category);
    List<Post> findByTitleContainingOrderByCreatedAtDesc(String keyword);
    List<Post> findByUserUserIdOrderByCreatedAtDesc(Long userId);
}
