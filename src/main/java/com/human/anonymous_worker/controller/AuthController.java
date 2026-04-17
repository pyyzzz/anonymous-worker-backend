package com.human.anonymous_worker.controller;

import com.human.anonymous_worker.dto.request.LoginReqDto;
import com.human.anonymous_worker.dto.request.SignUpReqDto;
import com.human.anonymous_worker.dto.response.ApiResponse;
import com.human.anonymous_worker.dto.response.LoginResDto;
import com.human.anonymous_worker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
@Tag(name = "Auth", description = "회원가입 / 로그인 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(@RequestParam String email) {
        boolean available = authService.checkEmail(email);
        return ResponseEntity.ok(ApiResponse.ok(available ? "사용 가능한 이메일입니다." : "이미 사용 중인 이메일입니다.", available));
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignUpReqDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다."));
    }

    @Operation(summary = "로그인", description = "성공 시 userId, email, name, isAdmin, companyName, certStatus 반환")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResDto>> login(@RequestBody LoginReqDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("로그인 성공", authService.login(dto)));
    }
}
