package com.human.anonymous_worker.dto.response;

import com.human.anonymous_worker.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder; import lombok.Getter;
import java.time.LocalDateTime;

@Getter @Builder @Schema(description = "회원 정보 응답 DTO")
public class UserResDto {
    private Long userId;
    private String email;
    private String name;
    private Boolean isAdmin;        // boolean → Boolean
    private String companyName;
    private String certStatus;
    private LocalDateTime createdAt;

    public static UserResDto from(User u) {
        return UserResDto.builder()
                .userId(u.getUserId()).email(u.getEmail()).name(u.getName())
                .isAdmin(u.isAdmin()).companyName(u.getCompanyName())
                .certStatus(u.getCertStatus() != null ? u.getCertStatus().name() : "NONE")
                .createdAt(u.getCreatedAt()).build();
    }
}
