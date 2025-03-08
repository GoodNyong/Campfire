<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게임 리뷰 게시판</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" href="review/gameList.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
	<%-- <p>session : ${sessionScope }</p> --%>
	<section class="page-wrap">
		<div class="page-header">
			<h1>게임 목록</h1>

			<!-- 검색창 -->
			<div class="search-container">
				<input type="text" id="search-input" placeholder="게임 제목 또는 장르 검색">
				<button id="search-clear" style="display: none;">❌</button>
			</div>

			<!-- 게임 추가 버튼 (관리자만 보이도록) -->
			<c:if test="${sessionScope.role == 1}">
			    <div class="add-game">
			        <a href="gameEdit?new=true" class="add-game-btn">게임 추가</a>
			    </div>
			</c:if>
		</div>

		<!-- 게임 목록 -->
		<div class="game-list-container">
			<ul class="game-list">
				<c:forEach var="game" items="${gameList}">
					<li class="game-item"><a
						href="gameDetail?game_id=${game.game_id}">
							<div class="game-thumbnail">
								<img src="${game.game_still_cut}" alt="${game.game_name}">
							</div>
							<div class="game-info">
								<h3>${game.game_name}</h3>
								<p>
									<strong>장르:</strong> <span class="game-genre">${game.genre}</span>
								</p>
								<p>
									<strong>출시일:</strong> ${game.release_dt}
								</p>
								<p>
									<strong>평균 평점:</strong> ${game.avg_rating}
								</p>
							</div>

					</a></li>
				</c:forEach>
			</ul>
		</div>

		<!-- 페이징 -->
		<div class="pagination">
			<c:if test="${currentPage > 1}">
				<a href="gameList?page=1">처음</a>
				<a href="gameList?page=${currentPage - 1}">이전</a>
			</c:if>
			<span>페이지 ${currentPage}</span>
			<c:if test="${gameList.size() == 10}">
				<%-- 다음 페이지가 존재할 가능성이 있을 때만 보이도록 --%>
				<a href="gameList?page=${currentPage + 1}">다음</a>
			</c:if>
		</div>

	</section>

	<script>
	document.addEventListener("DOMContentLoaded", function() {
	    let searchInput = document.getElementById("search-input");
	    let clearButton = document.getElementById("search-clear");

	    // 🔹 검색 입력 시 필터링 및 초기화 버튼 표시
	    searchInput.addEventListener("input", function() {
	        filterGames();
	        clearButton.style.display = searchInput.value.length > 0 ? "inline-block" : "none";
	    });

	    // 🔹 검색 초기화 버튼 클릭 시 실행
	    clearButton.addEventListener("click", function() {
	        searchInput.value = "";
	        filterGames();
	        clearButton.style.display = "none"; // 검색 초기화 버튼 숨기기
	    });
	});

	// 🔹 검색 기능 (게임 제목 + 장르 필터링)
	function filterGames() {
	    let input = document.getElementById("search-input").value.toLowerCase();
	    let games = document.querySelectorAll(".game-item");

	    games.forEach(item => {
	        let title = item.querySelector("h3").textContent.toLowerCase(); // 게임 제목
	        let genre = item.querySelector(".game-genre").textContent.toLowerCase(); // 게임 장르

	        // 제목 또는 장르 중 하나라도 포함되면 표시
	        if (title.includes(input) || genre.includes(input)) {
	            item.style.display = "block";
	        } else {
	            item.style.display = "none";
	        }
	    });
	}

    </script>
</body>
</html>
