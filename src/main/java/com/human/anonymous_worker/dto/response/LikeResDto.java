package com.human.anonymous_worker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder; import lombok.Getter;

@Getter @Builder @Schema(description = "좋아요 토글 응답 DTO")
public class LikeResDto {
    @Schema(description = "현재 좋아요 여부 (true: 좋아요, false: 취소)", example = "true")
    private boolean liked;
    @Schema(description = "현재 좋아요 수", example = "42")
    private long likeCount;
}
