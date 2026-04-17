package com.human.anonymous_worker.controller;

import com.human.anonymous_worker.dto.response.ApiResponse;
import com.human.anonymous_worker.dto.response.LikeResDto;
import com.human.anonymous_worker.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/posts/{postId}/likes") @RequiredArgsConstructor
@Tag(name = "Like", description = "좋아요 토글 API")
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "좋아요 토글", description = "좋아요 → 취소, 취소 → 좋아요 자동 전환")
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<LikeResDto>> toggleLike(@PathVariable Long postId, @PathVariable Long userId) {
        // TODO: likeService.toggleLike() 구현 후 주석 제거
         return ResponseEntity.ok(ApiResponse.ok("좋아요 처리 완료", likeService.toggleLike(postId, userId)));
//        return ResponseEntity.ok(ApiResponse.fail("TODO: LikeService.toggleLike() 구현 필요"));
    }

    @Operation(summary = "좋아요 여부 확인")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> isLiked(@PathVariable Long postId, @PathVariable Long userId) {
        // TODO: likeService.isLiked() 구현 후 주석 제거
         return ResponseEntity.ok(ApiResponse.ok("좋아요 여부 확인", likeService.isLiked(postId, userId)));
//        return ResponseEntity.ok(ApiResponse.fail("TODO: LikeService.isLiked() 구현 필요"));
    }
}
