package com.human.anonymous_worker.controller;

import com.human.anonymous_worker.dto.request.CertificationReqDto;
import com.human.anonymous_worker.dto.response.ApiResponse;
import com.human.anonymous_worker.dto.response.CertificationResDto;
import com.human.anonymous_worker.service.CertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/certifications") @RequiredArgsConstructor
@Tag(name = "Certification", description = "회사 인증 신청 / 관리자 처리 API")
public class CertificationController {
    private final CertificationService certificationService;

    @Operation(summary = "인증 신청")
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<CertificationResDto>> apply(@PathVariable Long userId, @RequestBody CertificationReqDto dto) {
        return ResponseEntity.ok(ApiResponse.ok("인증 신청 완료", certificationService.applyCertification(userId, dto)));
//        return ResponseEntity.ok(ApiResponse.fail("TODO: CertificationService.applyCertification() 구현 필요"));
    }

    @Operation(summary = "내 인증 현황 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CertificationResDto>> getMyCertification(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.ok("인증 현황 조회", certificationService.getMyCertification(userId)));
//        return ResponseEntity.ok(ApiResponse.fail("TODO: CertificationService.getMyCertification() 구현 필요"));
    }

    @Operation(summary = "[관리자] 대기 중 목록 조회")
    @GetMapping("/admin/pending")
    public ResponseEntity<ApiResponse<List<CertificationResDto>>> getPendingList() {
//        return ResponseEntity.ok(ApiResponse.fail("TODO: CertificationService.getPendingList() 구현 필요"));
        return ResponseEntity.ok(ApiResponse.ok("대기 목록 조회", certificationService.getPendingList()));
    }

    @Operation(summary = "[관리자] 인증 승인")
    @PutMapping("/admin/{certId}/approve")
    public ResponseEntity<ApiResponse<CertificationResDto>> approve(@PathVariable Long certId) {
//        return ResponseEntity.ok(ApiResponse.fail("TODO: CertificationService.approveCertification() 구현 필요"));
        return ResponseEntity.ok(ApiResponse.ok("인증 승인 완료", certificationService.approveCertification(certId)));
    }

    @Operation(summary = "[관리자] 인증 거절")
    @PutMapping("/admin/{certId}/reject")
    public ResponseEntity<ApiResponse<CertificationResDto>> reject(@PathVariable Long certId) {
//        return ResponseEntity.ok(ApiResponse.fail("TODO: CertificationService.rejectCertification() 구현 필요"));
        return ResponseEntity.ok(ApiResponse.ok("인증 거절 완료", certificationService.rejectCertification(certId)));
    }
}
