package com.human.anonymous_worker.controller;

import com.human.anonymous_worker.dto.request.UserUpdateReqDto;
import com.human.anonymous_worker.dto.response.ApiResponse;
import com.human.anonymous_worker.dto.response.UserResDto;
import com.human.anonymous_worker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/users") @RequiredArgsConstructor
@Tag(name = "User", description = "회원 정보 조회 / 수정 API")
public class UserController {
    private final UserService userService;

    // [관리자] 전체 유저 목록 조회
    @Operation(summary = "[관리자] 전체 회원 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResDto>>> getUserList() {
        return ResponseEntity.ok(ApiResponse.ok("전체 회원 목록 조회 성공", userService.getUserList()));
    }

    // 단건 조회
    @Operation(summary = "회원 정보 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResDto>> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok("회원 정보 조회 성공", userService.getUser(userId)));
    }

    // 수정
    @Operation(summary = "회원 정보 수정")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResDto>> updateUser(
            @PathVariable Long userId, @RequestBody UserUpdateReqDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("회원 정보 수정 성공", userService.updateUser(userId, dto)));
    }
}
