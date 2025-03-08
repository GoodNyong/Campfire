<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네비게이션</title>
<style type="text/css">
    *{
    padding: 0;
    margin: 0;
    }
    .header{
        z-index: 3;
    }
    .header-top{
        display: flex;
        position: relative;
        align-items: center;
        /* background-image: url("/작업공간/배경2.png"); */
        background-repeat: no-repeat;
        background-size: 100% 98%;
        background-position: 100%;
        height: 200px;
        border-bottom: 1px solid orange;
    }
    .header .inner{
        position: relative;
        display: flex;
        align-items: center;
        margin: 0 auto;
        width: 1200px;
        height: 100%;
    }
    .inner h1{
        margin: 0 20px 0 0;
        width: 300px;
        height: 100%;
        
    }
    .inner h1 a{
        display: block;
        position: relative;
        top: 50px;
        align-items: center;
        justify-content: center;
        font-size: 55px;
        text-decoration: none;
        color: black;
    }
    .inner h1 img{
        width: 40%;
        vertical-align: middle;
        
    }
    .header-top .search{
        width: 580px;
        height: 60px;
        position: relative;
        left: 100px;
    }
    .header-top .search form{
        border: 2px solid rgb(247, 135, 65);
        border-radius: 33px;
        height: 60px;
        width: 580px;
        position: relative;
    }
    .header-top .search input{
        width: 530px;
        height: 60px;
        background-color: rgba(0, 0, 0, 0);
        position: relative;
        top: 1px;
        padding: 0 20px;
        font-size: 20px;
        text-align: center;
        outline: none;
        border: none;
    }
    
    .header-top .search button{
        position: relative;
        background-color: rgba(0, 0, 0, 0);
        top: 5px;
        width: 28px;
        height: 45px;
        border: 0;
        cursor: pointer;        
    }
    .header-top .search button img {
        position: relative;
        width: 100%;
        background-color: rgba(0, 0, 0, 1);
    }
    
    .header-nav{
        width: 100%;
        height: 65px;
        background-color: #fff;
        border-bottom: 2px solid brown;
    }
    .top-menu{
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0 auto;
        width: 100%;
        height: 100%;
    }
    .header-nav nav{
        display: flex;
        position: relative;
        justify-content: space-around;
        align-items: center;
        width: 80%;
        height: 50px;
        font-size: 18px;
        font-weight: bold;
    }
    .header-nav nav a{
        display: flex;
        align-items: center;
        justify-content: center;
        line-height: center;
        text-decoration: none;
        text-align: center;
        color : rgb(241, 190, 48);
    }
    .icon{
        display: flex;
        position: relative;
        left: 10%;
    }
    .icon a img{
        width: 65px;
        height: 100%;
        background-color: rgba(0, 0, 0, 0);
    }
    .mp{
        display: flex;
        margin: auto;
        width: 80%;
        height: auto;
        border: 3px solid red;
        
    }
    .svp{
        margin: 2px;
        width: 60%;
        border: 2px solid blue;
        
    }
    .svp .swiper .swiper-wrapper{
        mar
    }
    .svp .swiper .swiper-wrapper video{
        width: 80%;
        height: 400px;
    }
    .s_npp{
        margin: 2px;
        display: flex;
        flex-direction: column;
        width: 40%;
        border: 2px solid black;
    }
    .snp{
        border: 5px solid purple;
            height: 50%;
            margin: 2px;
              
    }
    .snp .h{
        display: inline-block;
        height: 50px;
        padding: 3px 0 0 17px;
        font-weight: 700;
        font-size: 22px;
        white-space: nowrap;
        letter-spacing: 2px;
    }
    .snp .h a{
        text-decoration: none;
    }
    .snp .np {
        list-style-type: none;
    }
    .snp .np li{
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        padding: 2px 5px 3px 50px;
        font-size: 15px;
        height: 30px;

    }
    .spp{
        height: 50%;
        margin: 2px;
        border: 5px solid black;
    }
    .spp .h{
        display: inline-block;
        height: 50px;
        padding: 3px 0 0 17px;
        font-weight: 700;
        font-size: 22px;
        white-space: nowrap;
        letter-spacing: 2px;
    }
    .spp .h a{
        text-decoration: none;
    }
    .spp .pp{
        list-style-type: none;
    }
    .spp .pp li{
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        padding: 2px 5px 3px 50px;
        font-size: 15px;
        height: 30px;
    }
    .slsp{
        margin: auto;
        width: 80%;
        border: 3px solid orange;
        height: auto;
    }
    .srvp{
        margin: auto;
        width: 80%;
        border: 3px solid pink;
        height: auto;
    }
    
</style>
</head>
<body>
	<div style="width: 100%;">
		<header class="header">
			<div class="header-top">
				<div class="inner logo-search">
					<h1>
						<a href="./main"><img src="./main/작업공간/모닥불.jfif" alt="logo">모닥불</a>
					</h1>
					<div class="search">
						<form action="#" method="get">
							<input type="search" class="text" name="ts" maxlength="255"
								autocomplete="off" placeholder="검색어를 입력해 주세요">
							<button type="submit" value="검색">
								<img src="./main/작업공간/검색.png" alt="돋보기">
							</button>
						</form>
					</div>

				</div>
			</div>
			<div class="header-nav">
				<div class="top-menu">
					<nav>
						<a href="#">게임</a> <a href="#">공지</a> <a href="/Campfire/newsList">뉴스</a>
						<a href="/Campfire/videoList">영상</a> <a href="/Campfire/gameList">리뷰</a> <a href="#">전체게시판</a>
						<div class="icon">
							<a href="./login"><img src="./main/작업공간/로그인.jfif" alt="로그인로고"></a>
						</div>
					</nav>
				</div>
			</div>
			</div>
		</header>
</body>
</html>