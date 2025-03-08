<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게임 뉴스</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" href="news/newsList.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
	<section>
		<div class="page-wrap">
			<div class="page-header">
				<h1>게임 뉴스</h1>

				<!-- 검색/필터 섹션 -->
				<div class="filter-section search-filter-section">
					<div id="page-search" class="page-search">
						<!-- 검색 아이콘 (🔍) -->
						<span class="search-icon">🔍</span> <input id="page-search-input"
							type="text" class="page-search-input" placeholder="검색어를 입력하세요" />
						<!-- 초기화 버튼 (❌) -->
						<span id="page-search-clear" class="clear-icon"
							onclick="clearSearch()">❌</span>
					</div>
				</div>
			</div>
			<!-- page-header end -->

			<div class="page-body">
				<div class="news-list-wrap">
					<ul class="news-list">
						<c:choose>
							<c:when test="${not empty newsList}">
								<c:forEach var="news" items="${newsList}">
									<li class="news-item"><a href="${news.link}"
										target="_blank" class="news-title">${news.title}</a>
										<p class="news-description">${news.description}</p> <span
										class="news-date">🕒 ${news.pubDate}</span></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<p>뉴스를 불러올 수 없습니다.</p>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
			<!-- /.page-body -->
		</div>
		<!-- /.page-wrap -->
	</section>

	<script type="text/javascript">
  document.addEventListener("DOMContentLoaded", function() {
      let searchInput = document.getElementById("page-search-input");
      let clearButton = document.getElementById("page-search-clear");

      // 검색 입력 시 필터링 및 초기화 버튼 표시
      searchInput.addEventListener("input", function() {
          filterNews();
      });

      // 검색 초기화 버튼 클릭 시 실행
      clearButton.addEventListener("click", function() {
          clearSearch();
      });
  });

  // 🔹 검색 기능 (검색어 입력 시 뉴스 제목 및 내용 필터링)
  function filterNews() {
      let input = document.getElementById("page-search-input").value.toLowerCase();
      let newsItems = document.querySelectorAll(".news-item");
      let clearButton = document.getElementById("page-search-clear");

      newsItems.forEach(item => {
          let title = item.querySelector(".news-title").textContent.toLowerCase();
          let description = item.querySelector(".news-description").textContent.toLowerCase();

          if (title.includes(input) || description.includes(input)) {
              item.style.display = "block";
          } else {
              item.style.display = "none";
          }
      });

      // 검색창이 비어 있으면 '초기화' 버튼 숨김
      clearButton.style.display = input.length > 0 ? "block" : "none";
  }

  // 🔹 검색어 초기화 (초기화 버튼 클릭 시 실행)
  function clearSearch() {
      let searchInput = document.getElementById("page-search-input");
      searchInput.value = ""; // 입력 값 초기화
      filterNews(); // 전체 목록 다시 표시
  }
  </script>

</body>
</html>
