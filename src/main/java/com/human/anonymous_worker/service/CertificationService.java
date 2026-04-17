package com.human.anonymous_worker.service;

import com.human.anonymous_worker.dto.request.CertificationReqDto;
import com.human.anonymous_worker.dto.response.CertificationResDto;
import com.human.anonymous_worker.entity.Certification;
import com.human.anonymous_worker.entity.User;
import com.human.anonymous_worker.entity.enums.CertStatus;
import com.human.anonymous_worker.repository.CertificationRepository;
import com.human.anonymous_worker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;

    /**
     * 회사 인증 신청
     * TODO: 이미 신청한 경우 예외 발생, 아니면 Certification 엔티티 생성 후 저장
     */
    public CertificationResDto applyCertification(Long userId, CertificationReqDto dto) {
        // TODO: certificationRepository.existsByUserUserId() 로 중복 신청 확인
        if (certificationRepository.existsByUserUserId(userId)) {
            throw new IllegalArgumentException("이미 인증을 신청 하셨습니다");
        }
        User user = findUserById(userId);
        // TODO: 이미 신청한 경우 IllegalArgumentException("이미 인증 신청을 하셨습니다.")
        // TODO: Certification 엔티티 생성 (status: PENDING) 후 저장
        Certification cert = Certification.builder()
                .user(user)
                .realName(dto.getRealName())
                .age(dto.getAge())
                .companyName(dto.getCompanyName())
                .build();
        // TODO: user.certStatus = PENDING 으로 업데이트
        user.setCertStatus(CertStatus.PENDING);
        userRepository.save(user);
        Certification saved = certificationRepository.save(cert);

        return CertificationResDto.from(saved);
    }

    /**
     * 내 인증 현황 조회
     * TODO: userId로 Certification 조회 후 반환
     */
    @Transactional(readOnly = true)
    public CertificationResDto getMyCertification(Long userId) {
        Certification cert = certificationRepository.findByUserUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("인증 신청 내역이 없습니다."));
        return CertificationResDto.from(cert);
    }

    /**
     * [관리자] 대기 중인 인증 목록 조회
     * TODO: status가 PENDING인 목록 반환
     */
    @Transactional(readOnly = true)
    public List<CertificationResDto> getPendingList() {
        // TODO: findByStatusOrderByAppliedAtAsc(CertStatus.PENDING) 사용

        return certificationRepository.findByStatusOrderByAppliedAtAsc(CertStatus.PENDING)
                .stream()
                .map(CertificationResDto::from)
                .collect(Collectors.toList());
    }

    /**
     * [관리자] 인증 승인
     * TODO: 아래 흐름으로 구현하세요.
     * 1. certId로 Certification 조회
     * 2. certification.status = APPROVED + reviewedAt = 현재시각
     * 3. user.certStatus = APPROVED
     * 4. user.companyName = certification.companyName (복사)
     */
    public CertificationResDto approveCertification(Long certId) {
        // TODO: 구현
        Certification cert = certificationRepository.findById(certId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인증 신청입니다."));
        // 인증 상태 변경
        cert.setStatus(CertStatus.APPROVED);
        cert.setReviewedAt(LocalDateTime.now());

        // 유저 정보 동기화
        User user = cert.getUser();
        user.setCertStatus(CertStatus.APPROVED);
        user.setCompanyName(cert.getCompanyName()); // 회사명 복사
        userRepository.save(user);

        return CertificationResDto.from(certificationRepository.save(cert));
    }

    /**
     * [관리자] 인증 거절
     * TODO: 아래 흐름으로 구현하세요.
     * 1. certId로 Certification 조회
     * 2. certification.status = REJECTED + reviewedAt = 현재시각
     * 3. user.certStatus = REJECTED
     */
    public CertificationResDto rejectCertification(Long certId) {
        Certification cert = certificationRepository.findById(certId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인증 신청입니다."));

        cert.setStatus(CertStatus.REJECTED);
        cert.setReviewedAt(LocalDateTime.now());

        // 유저 certStatus → REJECTED
        User user = cert.getUser();
        user.setCertStatus(CertStatus.REJECTED);
        userRepository.save(user);

        return CertificationResDto.from(certificationRepository.save(cert));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. userId=" + userId));
    }
}
