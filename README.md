# anonymous-worker-backend

익명 직장인 커뮤니티 백엔드 서버입니다.

---

## 기술 스택

| 항목        | 버전            |
| ----------- | --------------- |
| Java        | 17              |
| Spring Boot | latest          |
| Build Tool  | Gradle          |
| Database    | MySQL           |
| ORM         | JPA / Hibernate |
| API Docs    | Swagger         |

---

## 로컬 환경 세팅

### 1. 사전 준비

- Java 17 설치
- MySQL 설치 및 실행
- IntelliJ IDEA 설치

### 2. 레포지토리 클론

```bash
git clone https://github.com/pyyzzz/anonymous-worker-backend.git
cd anonymous-worker-backend
```

### 3. MySQL DB 생성

```sql
CREATE DATABASE anonymous_worker;
```

### 4. application.properties 수정

`src/main/resources/application.properties` 파일에서 본인 DB 비밀번호로 변경

```properties
spring.datasource.password=본인비밀번호
```

### 5. IntelliJ에서 프로젝트 열기

1. IntelliJ → `Open` → 프로젝트 폴더 선택
2. `build.gradle` 우클릭 → `Link Gradle Project` 클릭
3. Gradle 의존성 자동 다운로드 완료 후 실행

### 6. 서버 실행

```bash
./gradlew bootRun
```

또는 IntelliJ에서 `AnonymousWorkerApplication.java` 실행 버튼 클릭

---

## API 문서

서버 실행 후 아래 주소에서 확인

```
http://localhost:8113/swagger-ui.html
```

---

## 기본 설정 정보

| 항목          | 값               |
| ------------- | ---------------- |
| 서버 포트     | 8113             |
| DB 이름       | anonymous_worker |
| DB 유저       | root             |
| 관리자 이메일 | admin@404.com    |

---

## 브랜치 전략

| 브랜치 | 설명             |
| ------ | ---------------- |
| `main` | 최종 배포 브랜치 |
