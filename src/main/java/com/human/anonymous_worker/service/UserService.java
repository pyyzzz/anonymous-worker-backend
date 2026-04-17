package com.human.anonymous_worker.service;

import com.human.anonymous_worker.dto.request.UserUpdateReqDto;
import com.human.anonymous_worker.dto.response.UserResDto;
import com.human.anonymous_worker.entity.User;
import com.human.anonymous_worker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j @Service @RequiredArgsConstructor @Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 단건 조회
    @Transactional(readOnly = true)
    public UserResDto getUser(Long userId) {
        return UserResDto.from(findUserById(userId));
    }

    // 전체 유저 목록 조회 (관리자용)
    @Transactional(readOnly = true)
    public List<UserResDto> getUserList() {
        return userRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(UserResDto::from)
                .collect(Collectors.toList());
    }

    // 수정
    public UserResDto updateUser(Long userId, UserUpdateReqDto dto) {
        User user = findUserById(userId);
        if (dto.getName() != null && !dto.getName().isBlank()) user.setName(dto.getName());
        if (dto.getPassword() != null && !dto.getPassword().isBlank())
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return UserResDto.from(userRepository.save(user));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. userId=" + userId));
    }
}
