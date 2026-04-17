package com.human.anonymous_worker.dto.request;

import com.human.anonymous_worker.entity.enums.PostCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Schema(description = "게시글 등록/수정 요청 DTO")
public class PostReqDto {
    @Schema(description = "작성자 userId", example = "1") private Long userId;
    @Schema(example = "오늘 회사에서 있었던 일") private String title;
    @Schema(example = "정말 공감되는 일이 있었어요...") private String content;
    @Schema(description = "카테고리 (FREE/QNA/INFO)", example = "FREE")
    private PostCategory category;
}
