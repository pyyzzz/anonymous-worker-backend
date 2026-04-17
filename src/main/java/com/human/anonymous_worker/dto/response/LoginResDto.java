package com.human.anonymous_worker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder; import lombok.Getter;

@Getter @Builder
@Schema(description = "로그인 응답 DTO - React localStorage에 저장")
public class LoginResDto {
    private Long userId;
    private String email;
    private String name;
    private Boolean isAdmin;        // boolean → Boolean (JSON 직렬화 시 "isAdmin" 으로 나옴)
    private String companyName;
    private String certStatus;
}
