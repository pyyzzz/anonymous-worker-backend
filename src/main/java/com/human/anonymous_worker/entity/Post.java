package com.human.anonymous_worker.entity;

import com.human.anonymous_worker.entity.enums.PostCategory;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 게시글 엔티티
 * [베이스 공통] postId, user, title, content, createdAt, updatedAt
 * [3조 추가]   category(ENUM), viewCount(조회수), likeCount(좋아요 캐싱)
 */
@Entity
@Table(name = "posts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    // ===== 3조 추가 필드 =====
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PostCategory category = PostCategory.FREE;  // 기본값: 전체게시판

    @Column(name = "view_count")
    @Builder.Default
    private Integer viewCount = 0;      // 조회수 (상세 조회 시 +1)

    @Column(name = "like_count")
    @Builder.Default
    private Integer likeCount = 0;      // 좋아요 수 (Like 테이블과 동기화)

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }

    @PreUpdate
    public void preUpdate() { this.updatedAt = LocalDateTime.now(); }
}
