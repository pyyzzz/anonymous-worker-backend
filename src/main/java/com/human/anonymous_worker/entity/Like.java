package com.human.anonymous_worker.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 좋아요 엔티티 (User ↔ Post 다대다 중간 테이블)
 * UNIQUE(user_id, post_id) → 한 유저가 같은 글에 중복 좋아요 방지
 *
 * TODO: LikeService의 토글 로직을 구현하세요.
 */

@Entity
@Table(name = "likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
