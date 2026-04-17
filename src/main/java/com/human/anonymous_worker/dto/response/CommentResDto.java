package com.human.anonymous_worker.dto.response;

import com.human.anonymous_worker.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder; import lombok.Getter;
import java.time.LocalDateTime;

@Getter @Builder @Schema(description = "댓글 응답 DTO")
public class CommentResDto {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String userName;
    private String maskedEmail;   // 추가: ho**** 형태로 마스킹
    private String companyName;
    private String content;
    private LocalDateTime createdAt;

    public static CommentResDto from(Comment c) {
        return CommentResDto.builder()
                .commentId(c.getCommentId())
                .postId(c.getPost().getPostId())
                .userId(c.getUser().getUserId())
                .userName(c.getUser().getName())
                .maskedEmail(maskEmail(c.getUser().getEmail()))  // 추가
                .companyName(c.getUser().getCompanyName())
                .content(c.getContent())
                .createdAt(c.getCreatedAt())
                .build();
    }

    // 이메일 마스킹: hong123@gmail.com → ho****
    private static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        String id = email.split("@")[0];
        return id.length() > 2 ? id.substring(0, 2) + "****" : id + "****";
    }
}
