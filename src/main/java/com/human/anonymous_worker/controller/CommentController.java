package com.human.anonymous_worker.controller;

import com.human.anonymous_worker.dto.request.CommentReqDto;
import com.human.anonymous_worker.dto.response.ApiResponse;
import com.human.anonymous_worker.dto.response.CommentResDto;
import com.human.anonymous_worker.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/posts/{postId}/comments") @RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글 CRUD API")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResDto>> saveComment(@PathVariable Long postId, @RequestBody CommentReqDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("댓글 등록 성공", commentService.saveComment(postId, dto)));
    }

    @Operation(summary = "댓글 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResDto>>> getCommentList(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.ok("댓글 목록 조회 성공", commentService.getCommentList(postId)));
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResDto>> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentReqDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("댓글 수정 성공", commentService.updateComment(commentId, dto)));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok(ApiResponse.ok("댓글 삭제 성공"));
    }
}
