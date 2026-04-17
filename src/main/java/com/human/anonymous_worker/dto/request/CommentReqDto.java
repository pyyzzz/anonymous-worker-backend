package com.human.anonymous_worker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CommentReqDto {
    @Schema(description = "작성자 userId", example = "1") private Long userId;
    @Schema(example = "완전 공감해요!") private String content;
}
