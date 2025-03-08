<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모닥불 - 게임 상세 정보</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" href="review/gameDetail.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
	<%-- <p>session : ${sessionScope }</p>
	<p>request : ${requestScope}</p> --%>
	<section class="page-wrap">
		<div class="game-detail">
			<h1>${game.game_name}</h1>
			<img src="${game.game_still_cut}" alt="${game.game_name}">
			<p>
				<strong>장르:</strong> ${game.genre}
			</p>
			<p>
				<strong>출시일:</strong> ${game.release_dt}
			</p>
			<p>
				<strong>평균 평점:</strong> ${game.avg_rating}
			</p>
			<p>${game.description}</p>

			<!-- 🔹 수정/삭제 버튼 (관리자만 가능) -->
			<c:if test="${sessionScope.role == 1}">
			    <div class="game-actions">
			        <a href="gameEdit?game_id=${game.game_id}" class="edit-btn">수정</a>
			        <a href="gameDelete?game_id=${game.game_id}" class="delete-btn"
			            onclick="return confirm('삭제하시겠습니까?')">삭제</a>
			    </div>
			</c:if>
		</div>

		<!-- 리뷰 작성 폼 -->
		<div class="review-form">
			<h3>리뷰 작성</h3>
			<form action="reviewSave" method="post">
				<input type="hidden" name="game_id" value="${game.game_id}">
				<!-- ✅ 추가 -->
				<input type="hidden" name="member_id" value="${sessionScope.member_id}">
				<label>평점:</label> <select name="rating">
					<c:forEach var="i" begin="1" end="5">
						<option value="${i}">${i}점</option>
					</c:forEach>
				</select>
				<textarea name="content" placeholder="리뷰를 입력하세요." required></textarea>
				<button type="submit">작성</button>
			</form>
		</div>

		<!-- 리뷰 목록 -->
		<div class="review-list">
			<h3>리뷰 목록</h3>
			<c:forEach var="entry" items="${reviewsWithUsernames}">
				<c:set var="review" value="${entry.key}" />
				<c:set var="username" value="${entry.value}" />
				<div class="review-item">
					<p>
						<strong>평점:</strong> ${review.rating}점
					</p>
					<p><!-- ✅ 작성자의 username 표시 -->
						<strong>작성자:</strong> ${username}
					</p>
					<p>${review.content}</p>
					<p>
						<strong>작성 날짜:</strong> ${review.reg_date}
					</p>
					<!-- 🔹 리뷰 수정/삭제 버튼 -->
					<c:if test="${sessionScope.member_id == review.member_id or sessionScope.role == 1}">
					    <div class="review-actions">
					        <a href="reviewEdit?review_id=${review.review_id}&game_id=${game.game_id}" class="edit-btn">수정</a>
					        <a href="reviewDelete?review_id=${review.review_id}&game_id=${game.game_id}" class="delete-btn"
					            onclick="return confirm('삭제하시겠습니까?')">삭제</a>
					    </div>
				    </c:if>
				</div>
			</c:forEach>
		</div>
	</section>
</body>
</html>
