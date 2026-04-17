package com.human.anonymous_worker.dto.response;

import com.human.anonymous_worker.entity.Post;
import com.human.anonymous_worker.entity.enums.PostCategory;
import com.human.anonymous_worker.repository.CommentRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "게시글 응답 DTO")
public class PostResDto {
    private Long postId;
    private Long userId;
    private String userName;
    private String companyName;
    private String maskedEmail;
    private String title;
    private String content;
    private PostCategory category;
    private Integer viewCount;
    private Integer likeCount;
    private Long commentCount;      // 댓글 수 추가
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // commentRepository 없이 호출하는 기본 변환 (commentCount = 0)
    public static PostResDto from(Post p) {
        return PostResDto.builder()
                .postId(p.getPostId())
                .userId(p.getUser().getUserId())
                .userName(p.getUser().getName())
                .companyName(p.getUser().getCompanyName())
                .maskedEmail(maskEmail(p.getUser().getEmail()))
                .title(p.getTitle()).content(p.getContent())
                .category(p.getCategory())
                .viewCount(p.getViewCount()).likeCount(p.getLikeCount())
                .commentCount(0L)
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .build();
    }

    // commentRepository 포함해서 호출하는 변환 (댓글 수 집계) // 오버로딩
    public static PostResDto from(Post p, CommentRepository commentRepository) {
        return PostResDto.builder()
                .postId(p.getPostId())
                .userId(p.getUser().getUserId())
                .userName(p.getUser().getName())
                .companyName(p.getUser().getCompanyName())
                .maskedEmail(maskEmail(p.getUser().getEmail()))
                .title(p.getTitle()).content(p.getContent())
                .category(p.getCategory())
                .viewCount(p.getViewCount()).likeCount(p.getLikeCount())
                .commentCount(commentRepository.countByPostPostId(p.getPostId()))
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .build();
    }

    // 이메일 마스킹: hong123@gmail.com → ho****
    private static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        String id = email.split("@")[0];
        return id.length() > 2 ? id.substring(0, 2) + "****" : id + "****";
    }
}
