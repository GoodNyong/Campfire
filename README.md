# Campfire

JSP/Servlet 기반의 게임 커뮤니티 웹 애플리케이션입니다. 회원 관리, 게시판/댓글, 리뷰/게임 관리와 함께 외부 API(YouTube, Naver 뉴스)를 주기적으로 수집하여 메인 콘텐츠로 제공합니다.

## 주요 기능
- 회원: 회원가입, 로그인/로그아웃, 마이페이지, 관리자 페이지(회원 조회/관리)
- 게시판: 목록/검색/페이지네이션, 글쓰기/수정/삭제, 조회수 증가, 댓글 CRUD
- 리뷰/게임: 등록/수정/삭제/상세
- 미디어/뉴스: YouTube 인기 게임 영상, Naver 게임 뉴스 수집 및 목록 제공
- 스케줄러: 애플리케이션 시작 시 일일 작업 실행(뉴스/영상 갱신)

## 기술 스택
- 언어/런타임: Java 17, JSP/Servlet (Tomcat 9)
- 데이터베이스: MySQL 8.x
- 라이브러리: JSTL 1.2, json-simple 1.1.1, MySQL Connector/J
- 프로젝트 타입: Eclipse Dynamic Web Project (빌드 도구 없음)

## 디렉터리 구조
```
src/
  main/java/
    controller/   # 서블릿 컨트롤러
    dao/          # DB 접근(DAO)
    dto/          # DTO/VO
    filter/       # 인증 등 웹 필터 (현재 주석 상태)
    listener/     # 앱 생명주기 리스너(AppContextListener)
    scheduler/    # 일일 작업(뉴스/영상 수집)
    service/      # 외부 API 서비스(YouTube/Naver)
    util/         # JDBC 유틸, 페이징, JS 알림 등
  main/webapp/
    WEB-INF/      # web.xml, 라이브러리 JAR
    board/, members/, review/, news/, video/, main/ # JSP/CSS/정적 파일
build/classes/     # 컴파일 산출물(저장소에 포함되어 있음)
```

## 빠른 시작
사전 준비물
- JDK 17, Apache Tomcat 9
- MySQL 8.x (로컬 `campfire` 스키마)

1) 데이터베이스 준비
- MySQL에 `campfire` 데이터베이스를 생성하고, 애플리케이션에서 사용하는 테이블이 존재하도록 준비합니다.
  - 코드에서 참조하는 주요 테이블: `members`, `boards`, `comments`, `video`, `news` 등
  - 컬럼은 DAO 코드에서 확인 가능합니다(예: `src/main/java/dao/*.java`).

2) DB 연결 설정(web.xml)
- 현재 프로젝트는 컨텍스트 파라미터로 DB 정보를 주입합니다. 예시:
```xml
<context-param>
  <param-name>MySqlDriver</param-name>
  <param-value>com.mysql.cj.jdbc.Driver</param-value>
</context-param>
<context-param>
  <param-name>MySqlURL</param-name>
  <param-value>jdbc:mysql://localhost:3306/campfire?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC</param-value>
</context-param>
<context-param>
  <param-name>MySqlId</param-name>
  <param-value>YOUR_DB_USER</param-value>
</context-param>
<context-param>
  <param-name>MySqlPwd</param-name>
  <param-value>YOUR_DB_PASSWORD</param-value>
</context-param>
```
- 보안을 위해 실제 배포 시에는 비밀정보를 코드/리포에 두지 말고, 서버 환경변수 또는 JNDI DataSource 사용을 권장합니다.

3) 배포/실행
- Eclipse: Tomcat 9 서버에 프로젝트를 Add하여 실행
- 수동 배포: `war` 패키징 후 Tomcat `webapps/` 배치(본 프로젝트는 빌드 스크립트가 없어 IDE 사용을 권장)

## 주요 엔드포인트(예)
- 회원: `/register`, `/login`, `/logout`, `/myPage`, `/adminPage`
- 게시판: `/BoardList`, `/View`, `/Write`, `/BoardUpdate`, `/BoardDelete`
- 댓글: `/Comment`, `/CommentUpdate`, `/CommentDelete`
- 리뷰/게임: `/gameList`, `/gameDetail`, `/gameEdit`, `/gameSave`, `/review*`
- 미디어/뉴스: `/videoList`, `/newsList`

애플리케이션 기동 시 `listener.AppContextListener`가 스케줄러(`DailyVideoTask`, `DailyNewsTask`)를 시작합니다. 외부 API 호출을 위해 네트워크와 API 키 설정이 필요합니다.

## 환경 설정(외부 API)
- Naver 뉴스 API: `service/NewsAPIService.java`에서 Client ID/Secret 사용
- YouTube Data API v3: `service/YouTubeAPIService.java`에서 API Key 사용

현재 예제 코드는 키가 하드코딩되어 있습니다. 운영에서는 다음 중 하나로 외부화하세요.
- 환경변수 로딩, `web.xml` 컨텍스트 파라미터, 또는 JNDI 환경 항목
- 키는 절대 저장소에 커밋하지 않습니다

## 보안/운영 고려사항(중요)
- 비밀번호 보안: 현재 평문 저장/검증. BCrypt/Argon2 해시로 교체 필요(가입/로그인 로직 수정).
- 비밀정보: DB 계정/외부 API 키가 코드/설정에 노출됨 → 환경변수/JNDI로 이동하고 커밋 금지.
- ✅ ~~SQL 인젝션: `BoardDao.selectCount(...)` 등 문자열 결합 쿼리 존재~~ → **수정 완료** (PreparedStatement 파라미터 바인딩 적용)
- ✅ ~~드라이버/URL: deprecated 드라이버 클래스 사용~~ → **수정 완료** (`com.mysql.cj.jdbc.Driver` 사용)
- 커넥션/자원: 스케줄러 포함 모든 DAO에서 사용 후 `close()` 보장, 풀(DataSource) 사용 권장.
- 인증 적용: `filter/AuthFilter`가 주석 상태. 보호 URL에 대해 일괄 필터 적용 필요.
- 테스트/스크립틀릿: 운영 노출 위험이 있는 테스트 JSP(`ConnectionTest.jsp`) 제거 권장. JSP 스크립틀릿 최소화.
- 저장소 용량: `build/` 산출물과 대용량 미디어(mp4 등)가 포함됨 → `.gitignore` 추가 및 외부 스토리지/CDN 사용 권장.
- 리다이렉트 안전성: 로그인 후 `url` 파라미터 검증/화이트리스트로 오픈 리다이렉트 방지.
- 로깅: `System.out.println` 대신 SLF4J/Logback 등 표준 로깅 도입.
- ✅ ~~중복 코드: `dto.PageHandler`와 `util.PageHandler` 중복~~ → **수정 완료** (통합 완료)

## 최근 개선 사항
### 2025-11-12 리팩토링
- **보안 개선**: BoardDao.selectCount에서 SQL 인젝션 취약점 수정 (PreparedStatement 파라미터 바인딩 적용)
- **코드 품질**: 중복된 util/PageHandler.java 제거 (dto 패키지로 통합)
- **최신화**: Deprecated MySQL 드라이버 클래스명 업데이트 (com.mysql.cj.jdbc.Driver 사용)
- **코드 정리**: 불필요한 TODO 주석 및 주석 처리된 코드 제거

## 로드맵(개선 제안)
1) 보안 강화
   - ✅ SQL 인젝션 제거 (BoardDao 일부 완료)
   - 🔲 비밀번호 해시 (BCrypt/Argon2 적용)
   - 🔲 비밀정보 외부화 (환경변수/JNDI)
   - 🔲 인증 필터 적용
2) 인프라
   - 🔲 JNDI DataSource/HikariCP
   - 🔲 표준 로깅 (SLF4J/Logback)
   - 🔲 오류 처리 일원화
3) 코드 정리
   - ✅ 페이징/유틸 통합 (중복 제거 완료)
   - 🔲 스크립틀릿 제거
   - 🔲 DAO try-with-resources 적용
4) 저장소 정리
   - 🔲 `.gitignore` 도입
   - 🔲 산출물/대용량 자산 분리
5) 문서화
   - 🔲 DB 스키마/ERD
   - 🔲 로컬 개발 가이드
   - 🔲 배포 절차 추가

## 라이선스
- 명시된 라이선스가 없으며, 내부 프로젝트 기준으로 사용됩니다.
