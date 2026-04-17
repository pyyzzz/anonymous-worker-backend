package com.human.anonymous_worker.entity.enums;

/**
 * 회사 인증 상태
 * NONE     : 미신청 또는 미인증 (화면에 [무소속] 표시)
 * PENDING  : 인증 신청 후 대기 중
 * APPROVED : 인증 완료 (화면에 [회사명] 표시)
 * REJECTED : 인증 거절
 */
public enum CertStatus {
    NONE, PENDING, APPROVED, REJECTED
}
