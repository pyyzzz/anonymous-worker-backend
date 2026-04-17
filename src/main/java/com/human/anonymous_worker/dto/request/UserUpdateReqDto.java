package com.human.anonymous_worker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserUpdateReqDto {
    @Schema(example = "홍길동2") private String name;
    @Schema(example = "5678") private String password;
}
