package com.human.anonymous_worker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignUpReqDto {
    @Schema(example = "hong@company.com") private String email;
    @Schema(example = "1234") private String password;
    @Schema(example = "홍길동") private String name;
}
