package com.human.anonymous_worker.service;

import com.human.anonymous_worker.dto.request.LoginReqDto;
import com.human.anonymous_worker.dto.request.SignUpReqDto;
import com.human.anonymous_worker.dto.response.LoginResDto;
import com.human.anonymous_worker.entity.User;
import com.human.anonymous_worker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j @Service @RequiredArgsConstructor @Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // application.properties 의 admin.email 값을 주입
    @Value("${admin.email}")
    private String adminEmail;

    public boolean checkEmail(String email) {
        return !userRepository.existsByEmail(email);
    }

    public void signUp(SignUpReqDto dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");

        // 관리자 전용 이메일로 가입하면 isAdmin=true 자동 부여
        boolean isAdmin = adminEmail.equalsIgnoreCase(dto.getEmail().trim());

        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .isAdmin(isAdmin)
                .build());

        log.info("회원가입 완료: email={}, isAdmin={}", dto.getEmail(), isAdmin);
    }

    @Transactional(readOnly = true)
    public LoginResDto login(LoginReqDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");

        return LoginResDto.builder()
                .userId(user.getUserId()).email(user.getEmail())
                .name(user.getName()).isAdmin(user.isAdmin())
                .companyName(user.getCompanyName())
                .certStatus(user.getCertStatus() != null ? user.getCertStatus().name() : "NONE")
                .build();
    }
}
