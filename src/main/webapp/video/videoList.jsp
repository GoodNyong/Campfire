<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유튜브 인기 게임 영상</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" href="video/videoList.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
	<section>
		<div class="page-wrap">
			<div class="page-header">
				<h1>실시간 유튜브 인기 영상</h1>

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
				<div class="video-rank-wrap">
					<div class="video-rank">
						<!-- 메인 목록 -->
						<div class="video-area-wrap" id="content-wrap">
							<div class="rank-text"></div>

							<div class="video-list-area">
								<ul class="video-list lists" id="ranking-video"
									data-are-more="true">
									<c:choose>
										<c:when test="${not empty videoList}">
											<c:forEach var="v" items="${videoList}">
												<li class="video-item item">
													<!-- 썸네일/영상 래퍼 -->
													<div
														class="youtube-video-wrapper kol-youtube-video-wrapper unload">
														<img class="thumb" src="${v.thumbnail_url}" alt="thumb"
															onclick="window.open('${v.video_url}', '_blank');">
													</div> <!-- 동영상 정보 -->
													<div class="detail-area" style="margin-top: 10px;">
														<a class="video-title" href="${v.video_url}"
															target="_blank" style="text-decoration: underline;">
															${v.title} </a>
														<div class="video-data-wrap" style="margin-top: 5px;">
															<div class="profile"></div>
															<div class="video-data">
																<a class="detail-data name" target="_blank"
																	title="${v.channel_name}" href="${v.channel_url}">
																	${v.channel_name} </a>
																<div class="detail-data view">업로드일:
																	${v.published_dt}</div>
															</div>
														</div>
													</div>
												</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<p>영상을 불러오지 못했습니다.</p>
										</c:otherwise>
									</c:choose>
								</ul>
								<!-- /#ranking-video -->
							</div>
							<!-- /.video-list-area -->
						</div>
						<!-- /.video-area-wrap -->
					</div>
					<!-- /.video-rank -->
				</div>
				<!-- /.video-rank-wrap -->
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
	        filterVideos();
	    });

	    // 검색 초기화 버튼 클릭 시 실행
	    clearButton.addEventListener("click", function() {
	        clearSearch();
	    });
	});

//🔹 검색 기능 (검색어 입력 시 동영상 제목 및 채널명 필터링)
  function filterVideos() {
      let input = document.getElementById("page-search-input").value.toLowerCase();
      let videoItems = document.querySelectorAll(".video-item");
      let clearButton = document.getElementById("page-search-clear");

      let hasResults = false; // 검색 결과 여부 확인

      videoItems.forEach(item => {
          let title = item.querySelector(".video-title").textContent.toLowerCase();
          let channelName = item.querySelector(".detail-data.name").textContent.toLowerCase();

          // 제목 또는 채널명이 검색어를 포함하는 경우 표시
          if (title.includes(input) || channelName.includes(input)) {
              item.style.display = "block";
              hasResults = true;
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
	    filterVideos(); // 전체 목록 다시 표시
	}

  </script>
</body>
</html>
