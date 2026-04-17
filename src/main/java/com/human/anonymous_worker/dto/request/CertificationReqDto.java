package com.human.anonymous_worker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Schema(description = "회사 인증 신청 요청 DTO")
public class CertificationReqDto {
    @Schema(description = "실명", example = "홍길동") private String realName;
    @Schema(description = "나이", example = "30") private Integer age;
    @Schema(description = "회사명", example = "카카오") private String companyName;
}
