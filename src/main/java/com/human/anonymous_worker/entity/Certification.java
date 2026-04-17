package com.human.anonymous_worker.entity;

import com.human.anonymous_worker.entity.enums.CertStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 회사 인증 신청 엔티티
 * User(1:1) 관계 - 1인 1회 신청만 가능
 *
 * [인증 흐름]
 * 1. 유저가 신청 → status: PENDING
 * 2. 관리자 승인 → status: APPROVED + users.companyName, certStatus 동기화
 * 3. 관리자 거절 → status: REJECTED + users.certStatus 동기화
 *
 * TODO: CertificationService의 메서드를 구현하세요.
 */
@Entity
@Table(name = "certifications")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cert_id")
    private Long certId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;              // 신청한 유저 (1인 1회)

    @Column(name = "real_name", nullable = false, length = 50)
    private String realName;        // 실명

    @Column(nullable = false)
    private Integer age;            // 나이

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;     // 신청한 회사명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CertStatus status = CertStatus.PENDING;  // 처리 상태

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;    // 신청일

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;   // 관리자 처리일

    @PrePersist
    public void prePersist() { this.appliedAt = LocalDateTime.now(); }
}
