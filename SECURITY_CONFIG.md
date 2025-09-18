# 보안 설정 가이드 (Security Configuration Guide)

이 애플리케이션을 실행하기 전에 다음 환경변수들을 설정해야 합니다.

## 필수 환경변수 설정

### 1. 데이터베이스 설정
```bash
# 시스템 속성으로 설정
-Ddb.url=jdbc:mysql://localhost:3306/campfire
-Ddb.username=YOUR_DATABASE_USERNAME
-Ddb.password=YOUR_DATABASE_PASSWORD
```

### 2. YouTube API 키 설정
```bash
# YouTube Data API v3 키를 발급받아 설정하세요
-Dyoutube.api.key=YOUR_YOUTUBE_API_KEY
```

### 3. 네이버 API 설정  
```bash
# 네이버 개발자센터에서 발급받은 클라이언트 정보를 설정하세요
-Dnaver.client.id=YOUR_NAVER_CLIENT_ID
-Dnaver.client.secret=YOUR_NAVER_CLIENT_SECRET
```

## 설정 방법

### IDE에서 실행할 때
1. Run Configuration에서 VM Options에 위 시스템 속성들을 추가
2. 예: `-Ddb.username=myuser -Ddb.password=mypass -Dyoutube.api.key=mykey`

### 서버 배포시
1. 애플리케이션 서버의 환경변수로 설정
2. 또는 시스템 속성으로 JVM 시작시 설정
3. web.xml의 context-param 값들을 실제 값으로 변경

## 보안 주의사항
- **절대로** API 키나 패스워드를 소스코드에 하드코딩하지 마세요
- 프로덕션 환경에서는 반드시 강한 패스워드를 사용하세요  
- API 키는 필요한 권한만 부여하고 정기적으로 재발급하세요
- 데이터베이스 접근은 최소 권한 원칙을 따르세요

## API 키 발급 방법

### YouTube Data API v3
1. [Google Cloud Console](https://console.cloud.google.com/) 접속
2. 프로젝트 생성 또는 선택
3. YouTube Data API v3 활성화
4. 사용자 인증 정보에서 API 키 생성

### 네이버 검색 API
1. [네이버 개발자센터](https://developers.naver.com/) 접속
2. 애플리케이션 등록
3. 검색 API 사용 설정
4. 클라이언트 ID와 클라이언트 Secret 확인