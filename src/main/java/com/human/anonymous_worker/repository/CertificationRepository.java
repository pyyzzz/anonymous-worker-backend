package com.human.anonymous_worker.repository;

import com.human.anonymous_worker.entity.Certification;
import com.human.anonymous_worker.entity.enums.CertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    // 특정 유저의 인증 신청 조회
    Optional<Certification> findByUserUserId(Long userId);
    // 상태별 인증 목록 (관리자용)
    List<Certification> findByStatusOrderByAppliedAtAsc(CertStatus status);
    // 신청 여부 확인
    boolean existsByUserUserId(Long userId);
}
