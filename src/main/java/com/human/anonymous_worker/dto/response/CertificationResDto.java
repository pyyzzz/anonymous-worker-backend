package com.human.anonymous_worker.dto.response;

import com.human.anonymous_worker.entity.Certification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder; import lombok.Getter;
import java.time.LocalDateTime;

@Getter @Builder @Schema(description = "회사 인증 응답 DTO")
public class CertificationResDto {
    private Long certId;
    private Long userId;
    private String userName;
    private String realName;
    private Integer age;
    private String companyName;
    private String status;
    private LocalDateTime appliedAt;
    private LocalDateTime reviewedAt;

    public static CertificationResDto from(Certification c) {
        return CertificationResDto.builder()
                .certId(c.getCertId()).userId(c.getUser().getUserId())
                .userName(c.getUser().getName()).realName(c.getRealName())
                .age(c.getAge()).companyName(c.getCompanyName())
                .status(c.getStatus().name())
                .appliedAt(c.getAppliedAt()).reviewedAt(c.getReviewedAt()).build();
    }
}
