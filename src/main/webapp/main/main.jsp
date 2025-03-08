<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.net.*, java.io.*, org.json.*" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
    <link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
    <title>모닥불</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
    <link rel="stylesheet" type="text/css" href="main/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.0/css/all.min.css" integrity="sha512-9xKTRVabjVeZmc+GUW8GgSmcREDunMM+Dt/GrzchfN8tkwHizc5RP4Ok/MXFFy5rIjJjzhndFScTceq5e6GvVQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>|
	<!-- git test  -->
        <header>
                <div class="top-menu">
                    <div class="logo">
                            <a href="./main"><img src="main/imagefile/mainlogo.jpg"><span class="logoword">모닥불</span></a>
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

                    <!-- 검색창 -->
                    <div class="search-box">
                        <form action="" method="get">
                          <input class="search-txt" type="text" name="" maxlength="255" placeholder="검색어를 입력해 주세요">
                          <button class="search-btn" type="submit">
                            <i class="fas fa-search"></i>
                          </button>
                        </form>
                    </div>
                    
					<!-- 로그인 -->	
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
        <section>
            <div class="swiper mySwiper">
                <div class="swiper-wrapper">
                  <div class="swiper-slide">
                  <a href="https://www.leagueoflegends.com/ko-kr/" target="_blank">
                    <video src="main/imagefile/LoL.mp4" type="video/webm" muted autoplay loop playsinline>
                    </video></a>
                </div>
                  <div class="swiper-slide">
                    <a href="https://www.pubg.com/ko/main" target="_blank">
                        <video src="main/imagefile/battleground.mp4" type="video/webm" muted autoplay loop playsinline>
                        </video></a>
                </div>
                  <div class="swiper-slide"> 
                  <a href="https://m-lostark.game.onstove.com/Main" target="_blank">
                    <video src="main/imagefile/lostark.mp4" type="video/webm" muted autoplay loop playsinline>
                    </video></a>
                   </div>
                  <div class="swiper-slide"> 
                  <a href="https://maplestory.nexon.com/Home/Main" target="_blank">
                    <video src="main/imagefile/maple.mp4" type="video/webm" muted autoplay loop playsinline>
                    </video></a>
                   </div>
                </div>
                <div class="swiper-button-next"></div>
                <div class="swiper-button-prev"></div>
                <div class="swiper-pagination"></div>
              </div>
            <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
            <script>
                var swiper = new Swiper(".mySwiper", {
                    
                    loop: true,
                    
                    pagination: {
                        el: ".swiper-pagination",
                        type: "fraction",
                    },
                    navigation: {
                        nextEl: ".swiper-button-next",
                        prevEl: ".swiper-button-prev",

                    },
                });
            </script>
        </section>

        
        <footer>
  			<div class="footer-container">
  				<P>명칭 : 모닥불</P>
    			<p>충청북도 청주시 서원구 사직대로 109 4층</p>
    			<p>&copy; 2025 My Website. All rights reserved.</p>
    			<nav class="footer-nav">
      				<a href="#">Home</a>
    			</nav>
  			</div>
</footer>
</body>
</html>
