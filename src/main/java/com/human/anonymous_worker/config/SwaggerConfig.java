package com.human.anonymous_worker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("💼 익명 직장인 커뮤니티 API")
                        .description("""
                                익명 직장인 커뮤니티 서비스 API 명세서
                                
                                [구현 기능]
                                - Auth          : 회원가입 / 로그인
                                - User          : 회원 정보 조회 / 수정
                                - Post          : 게시글 CRUD (카테고리/조회수/좋아요 포함)
                                - Comment       : 댓글 CRUD
                                - Like          : 좋아요 토글       ← 구현 필요
                                - Certification : 회사 인증 신청/관리 ← 구현 필요
                                """)
                        .version("v1.0.0")
                        .contact(new Contact().name("3조 - 익명 직장인 커뮤니티")));
    }
}
