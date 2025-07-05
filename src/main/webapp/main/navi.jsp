<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", 0); // Proxies
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>네비게이션</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="main/main.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"
	integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<style type="text/css">
body {
	background-image: url("./main/imagefile/bgt.jpg");
	background-repeat: no-repeat;
	background-size: 100% 100%;
	background-attachment: fixed;
	margin: 0;
	padding: 95px;
}
</style>
</head>
<body>
	<header>
		<div class="top-menu">
			<div class="logo">
				<a href="./main"><img src="main/imagefile/mainlogo.jpg">모닥불</a>
			</div>
			<button class="hamburger-menu">
				<i class="fa-solid fa-bars"></i>
			</button>
			<div class="nav-menu">
				<ul>
					<li><a href="#">게시판</a>
						<ul>
							<li><a href="BoardList">전체 게시판</a></li>
							<li><a href="BoardList?category=2">리그오브레전드</a></li>
							<li><a href="BoardList?category=3">배틀그라운드</a></li>
							<li><a href="BoardList?category=4">로스트아크</a></li>
							<li><a href="BoardList?category=5">메이플스토리</a></li>
						</ul></li>
					<li><a href="BoardList?category=1">공지</a></li>
					<li><a href="newsList">뉴스</a></li>
					<li><a href="gameList">리뷰</a></li>
					<li><a href="videoList">영상</a></li>
				</ul>
			</div>
			<div class="search-box">
				<form action="#" method="get">
					<input class="search-txt" type="text" maxlength="255"
						placeholder="검색어를 입력해 주세요">
					<button class="search-btn" type="submit">
						<i class="fas fa-search"></i>
					</button>
				</form>
			</div>

			<div class="dropdown">
				<button class="dropbtn">
					<img src="main/imagefile/loginlogo.png">
				</button>
				<div class="dropdown-content">
					<%
					String loginUserId = (String) session.getAttribute("loginUserId");
					Integer role = (Integer) session.getAttribute("role");

					if (loginUserId != null) { // 로그인 상태
						if (role != null && role == 1) { // 🔹 관리자 계정
					%>
					<a href="adminPage">관리자 페이지</a> <a href="logout">로그아웃</a>
					<%
					} else { // 🔹 일반 회원 계정
					%>
					<a href="myPage">마이페이지</a> <a href="logout">로그아웃</a>
					<%
					}
					} else { // 비로그인 상태
					%>
					<a href="login">로그인</a> <a href="register">회원가입</a>
					<%
					}
					%>

				</div>
			</div>
			<!-- <script>
				// 햄버거 메뉴 토글
				$('.hamburger-menu').on('click', function(event) {
					event.stopPropagation();
					$('.nav-menu').toggleClass('active');
				});

				document.querySelector('.dropbtn').addEventListener('click',
						function(event) {
							var dropdownContent = this.nextElementSibling;
							if (dropdownContent.style.display === 'block') {
								dropdownContent.style.display = 'none';
							} else {
								dropdownContent.style.display = 'block';
							}
							event.stopPropagation(); //추가 
						});

				document.addEventListener('click', function() {
					var dropdowns = document
							.querySelectorAll('.dropdown-content');
					dropdowns.forEach(function(dropdown) {
						if (dropdown.style.display === 'block') {
							dropdown.style.display = 'none';
						}
					});
				});
			</script> -->
            <%-- ▼▼▼ [수정] 스크립트 전체를 jQuery(function($){...});로 감싸기 ▼▼▼ --%>
			<script>
                jQuery(function($){ // HTML 문서가 완전히 준비되면 내부 코드를 실행합니다.

                    // 햄버거 메뉴 토글
                    $('.hamburger-menu').on('click', function(event) {
                        event.stopPropagation();
                        $('.nav-menu').toggleClass('active');
                    });

                    // 로그인 드롭다운 토글
                    $('.dropbtn').on('click', function(event) {
                        var dropdownContent = $(this).next('.dropdown-content');
                        dropdownContent.toggle();
                        event.stopPropagation();
                    });

                    // 문서 다른 곳 클릭 시 열려있는 메뉴 닫기
                    $(document).on('click', function(event) {
                        // 햄버거 메뉴 닫기
                        if (!$(event.target).closest('.hamburger-menu, .nav-menu').length) {
                             $('.nav-menu').removeClass('active');
                        }
                        // 로그인 드롭다운 닫기
                        if (!$(event.target).closest('.dropdown').length) {
                            $('.dropdown-content').hide();
                        }
                    });

                });
			</script>
            <%-- ▲▲▲ [수정] 스크립트 전체를 jQuery(function($){...});로 감싸기 ▲▲▲ --%>
		</div>
	</header>
</body>
</html>
