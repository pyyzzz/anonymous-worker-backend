package com.human.anonymous_worker.repository;

import com.human.anonymous_worker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // 전체 유저 목록 (최신 가입순)
    List<User> findAllByOrderByCreatedAtDesc();
}
