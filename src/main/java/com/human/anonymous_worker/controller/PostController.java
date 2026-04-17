package com.human.anonymous_worker.controller;

import com.human.anonymous_worker.dto.request.PostReqDto;
import com.human.anonymous_worker.dto.response.ApiResponse;
import com.human.anonymous_worker.dto.response.PostResDto;
import com.human.anonymous_worker.entity.enums.PostCategory;
import com.human.anonymous_worker.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/posts") @RequiredArgsConstructor
@Tag(name = "Post", description = "게시글 CRUD API (카테고리/조회수/좋아요 포함)")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<PostResDto>> savePost(@RequestBody PostReqDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("게시글 등록 성공", postService.savePost(dto)));
    }

    @Operation(summary = "전체 목록 조회 (최신순)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResDto>>> getPostList() {
        return ResponseEntity.ok(ApiResponse.ok("게시글 목록 조회 성공", postService.getPostList()));
    }

    @Operation(summary = "단건 조회 (조회수 +1)")
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResDto>> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.ok("게시글 조회 성공", postService.getPost(postId)));
    }

    @Operation(summary = "카테고리별 조회", description = "FREE / QNA / INFO")
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<PostResDto>>> getPostListByCategory(@PathVariable PostCategory category) {
        return ResponseEntity.ok(ApiResponse.ok("카테고리별 조회 성공", postService.getPostListByCategory(category)));
    }

    @Operation(summary = "제목 검색")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PostResDto>>> searchPost(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.ok("검색 성공", postService.searchPost(keyword)));
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResDto>> updatePost(@PathVariable Long postId, @RequestBody PostReqDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("게시글 수정 성공", postService.updatePost(postId, dto)));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.ok(ApiResponse.ok("게시글 삭제 성공"));
    }
}
