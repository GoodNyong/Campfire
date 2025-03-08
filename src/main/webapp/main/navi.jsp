<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<style type="text/css">
	body{
	background-image: url("./main/imagefile/bgt.jpg");
	background-repeat:no-repeat;
	background-size: 100% 100%;
	background-attachment: fixed;
	margin: 0;
 	padding: 0;
	}
</style>
</head>
<body>
        <header>
                <div class="top-menu">
                    <div class="logo">
                            <a href="./main"><img src="main/imagefile/mainlogo.jpg"> 모닥불</a>
                    </div>
                    <div class="nav-menu">
                        <ul>
                            <li><a href="#">게시판</a>
                                <ul>
                                	<li><a href="BoardList">전체 게시판</a></li>
                                    <li><a href="BoardList?category=2">리그오브레전드</a></li>
                                    <li><a href="BoardList?category=3">배틀그라운드</a></li>
                                    <li><a href="BoardList?category=4">로스트아크</a></li>
                                    <li><a href="BoardList?category=5">메이플스토리</a></li>
                                </ul>
                            </li>
                                    <li><a href="BoardList?category=1">공지</a> </li>
                                    <li><a href="newsList">뉴스</a></li>
                                    <li><a href="gameList">리뷰</a> </li>
                                    <li><a href="videoList">영상</a></li>
                        </ul>
                    </div>
                   <div class="search-box">
                        <form action="#" method="get">
                          <input class="search-txt" type="text" maxlength="255" placeholder="검색어를 입력해 주세요">
                          <button class="search-btn" type="submit">
                            <i class="fas fa-search"></i>
                          </button>
                        </form>
                    </div>

                    <div class="dropdown">
                        <button class="dropbtn"><img src="main/imagefile/loginlogo.png"></button>
                        <div class="dropdown-content">
                             <%
                     String loginUserId = (String) session.getAttribute("loginUserId");
                     Integer role = (Integer) session.getAttribute("role");

                     if (loginUserId != null) { // 로그인 상태
                        if (role != null && role == 1) { // 🔹 관리자 계정
                     %>
                           <a href="adminPage">관리자 페이지</a> 
                           <a href="logout">로그아웃</a>
                     <%
                        } else { // 🔹 일반 회원 계정
                     %>
                           <a href="myPage">마이페이지</a> 
                           <a href="logout">로그아웃</a>
                     <%
                        }
                     } else { // 비로그인 상태
                     %>
                        <a href="login">로그인</a>
                        <a href="register">회원가입</a>
                     <%
                     }
                     %>

                        </div>
                    </div>
                    <script>
                        document.querySelector('.dropbtn').addEventListener('click', function(event) {
                        var dropdownContent = this.nextElementSibling;
                        if (dropdownContent.style.display === 'block') {
                            dropdownContent.style.display = 'none';
                        } else {
                            dropdownContent.style.display = 'block';
                        }
                        event.stopPropagation(); //추가 
                    });


                    document.addEventListener('click', function() {
                        var dropdowns = document.querySelectorAll('.dropdown-content');
                        dropdowns.forEach(function(dropdown) {
                            if (dropdown.style.display === 'block') {
                                dropdown.style.display = 'none';
                            }
                        });
                    });
                    </script>
                   
                </div>
        </header>
</body>
</html>
