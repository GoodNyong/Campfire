<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
    <title>모닥불</title>
    <link rel="stylesheet" type="text/css" href="main/main_quadrant.css"> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <%-- 내비게이션 바 포함 (수정 없음) --%>
    <jsp:include page="../main/navi.jsp" />

    <%-- 4분할 레이아웃을 감싸는 메인 컨테이너 --%>
    <main class="quadrant-container">

        <div class="quadrant quadrant-posts">
            <div class="quadrant-header">
                <h3><i class="fa-solid fa-bullhorn"></i> 공지 & 게시글</h3>
                <a href="BoardList" class="more-link">더보기 &gt;</a>
            </div>
            <ul class="post-list">
                <li><a href="#"><span class="notice-badge">공지</span> 2025년 7월 서버 정기 점검 안내</a><span class="post-date">07-05</span></li>
                <li><a href="#">새로운 '어둠의 전장' 업데이트 미리보기</a><span class="post-date">07-04</span></li>
                <li><a href="#">'전설의 대장장이' 이벤트 참여 방법</a><span class="post-date">07-03</span></li>
                <li><a href="#">길드전 시즌 3 보상 관련 안내입니다.</a><span class="post-date">07-02</span></li>
                <li><a href="#">운영 정책 위반 계정 제재 안내 (7/1)</a><span class="post-date">07-01</span></li>
                <li><a href="#">최고 레벨 달성! 저만의 공략 팁 공유</a><span class="post-date">06-30</span></li>
            </ul>
        </div>

        <div class="quadrant quadrant-reviews">
            <div class="quadrant-header">
                <h3><i class="fa-solid fa-gamepad"></i> 게임 리뷰</h3>
                <a href="gameList" class="more-link">더보기 &gt;</a>
            </div>
            <div class="card-grid review-grid">
                <div class="card">
                    <img src="main/imagefile/review_thumb_1.jpg" alt="게임 1 리뷰">
                    <div class="card-body">
                        <h5>사이버펑크 2077: 확장팩 리뷰</h5>
                        <div class="rating">
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star-half-stroke"></i>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <img src="main/imagefile/review_thumb_2.jpg" alt="게임 2 리뷰">
                    <div class="card-body">
                        <h5>젤다의 전설: 왕국의 눈물</h5>
                        <div class="rating">
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="quadrant quadrant-news">
            <div class="quadrant-header">
                <h3><i class="fa-solid fa-newspaper"></i> 최신 뉴스</h3>
                <a href="newsList" class="more-link">더보기 &gt;</a>
            </div>
            <ul class="news-list">
                <li>
                    <a href="#">
                        <img src="main/imagefile/news_thumb_1.jpg" alt="뉴스 썸네일 1">
                        <div class="news-text">
                            <p class="news-title">라이엇 게임즈, '발로란트' 신규 맵 '어비스' 공개</p>
                            <span class="news-source">게임인사이트 | 2025-07-05</span>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <img src="main/imagefile/news_thumb_2.jpg" alt="뉴스 썸네일 2">
                        <div class="news-text">
                            <p class="news-title">"한국이 제일 중요" 팰월드 대표, 지스타 참가 확정</p>
                            <span class="news-source">디스이즈게임 | 2025-07-04</span>
                        </div>
                    </a>
                </li>
                 <li>
                    <a href="#">
                        <img src="main/imagefile/news_thumb_3.jpg" alt="뉴스 썸네일 3">
                        <div class="news-text">
                            <p class="news-title">블리자드, '디아블로4' 첫 확장팩 출시일 발표</p>
                            <span class="news-source">인벤 | 2025-07-03</span>
                        </div>
                    </a>
                </li>
            </ul>
        </div>

        <div class="quadrant quadrant-videos">
            <div class="quadrant-header">
                <h3><i class="fa-solid fa-video"></i> 추천 영상</h3>
                <a href="videoList" class="more-link">더보기 &gt;</a>
            </div>
            <div class="card-grid video-grid">
                <div class="card">
                    <a href="#" class="video-link">
                        <img src="main/imagefile/video_thumb_1.jpg" alt="영상 1 썸네일">
                        <div class="play-icon"><i class="fa-solid fa-play"></i></div>
                        <h5>페이커 매드무비 #125</h5>
                    </a>
                </div>
                 <div class="card">
                    <a href="#" class="video-link">
                        <img src="main/imagefile/video_thumb_2.jpg" alt="영상 2 썸네일">
                        <div class="play-icon"><i class="fa-solid fa-play"></i></div>
                        <h5>로스트아크 '카멘' 최초 공략</h5>
                    </a>
                </div>
            </div>
        </div>
    </main>
</body>
</html>