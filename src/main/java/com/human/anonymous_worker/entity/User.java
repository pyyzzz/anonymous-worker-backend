package com.human.anonymous_worker.entity;

import com.human.anonymous_worker.entity.enums.CertStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 회원 엔티티
 * [베이스 공통] userId, email, password, name, isAdmin, createdAt
 * [3조 추가]   companyName(회사명), certStatus(인증 상태)
 */
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString(exclude = "password")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "is_admin")
    @Builder.Default
    private boolean isAdmin = false;

    // ===== 3조 추가 필드 =====
    @Column(name = "company_name", length = 100)
    private String companyName;     // 인증 승인 후 표시되는 회사명 (미인증: null)

    @Enumerated(EnumType.STRING)
    @Column(name = "cert_status")
    @Builder.Default
    private CertStatus certStatus = CertStatus.NONE; // 인증 상태

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }
}
