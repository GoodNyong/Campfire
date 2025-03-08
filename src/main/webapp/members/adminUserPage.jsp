	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page session="true"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%
	String loginUser = (String) session.getAttribute("loginUserId");
	Integer role = (Integer) session.getAttribute("role");

	// 관리자 여부 확인
	if (loginUser == null || role == null || role != 1) {
		response.sendRedirect("./login");
		return;
	}
	%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>모닥불 - 회원 마이페이지 조회 (관리자)</title>
	<link rel="stylesheet" type="text/css" href="./members/myPage.css">
	<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
	<!-- <style>
		/* 🔹 리뷰 목록 스타일 조정 */
		.review-list {
			width: 100%;
			border-collapse: collapse;
			margin-bottom: 20px;
		}

		.review-list th, .review-list td {
			border: 1px solid #ddd;
			padding: 10px;
			text-align: left;
		}

		.review-list th {
			background-color: #f4f4f4;
			font-weight: bold;
		}

		/* 🔹 리뷰 내용 스타일 */
		.review-content {
			white-space: pre-line;
		}

		/* 🔹 버튼 스타일 */
		.delete-btn {
			padding: 6px 10px;
			background-color: #dc3545;
			color: white;
			border: none;
			border-radius: 4px;
			cursor: pointer;
		}

		.delete-btn:hover {
			background-color: #c82333;
		}
	</style> -->
	<link rel="stylesheet" type="text/css" href="./members/myPage.css">
	</head>
	<body>
		<jsp:include page="../main/navi.jsp" />
		<div class="page-wrap">
			<h2>회원 마이페이지 조회 (관리자)</h2>

			<!-- 회원 정보 조회 -->
			<h3>회원 정보</h3>
			<form class="profile-form">
				<div class="profile-row">
					<label>아이디: </label>
					<input type="text" name="id" value="${viewingMember.id}" readonly>
				</div>
				<div class="profile-row">
					<label>현재 비밀번호: </label>
					<input type="text" name="password" value="${viewingMember.password}" readonly>
				</div>
				<div class="profile-row">
					<label>닉네임: </label>
					<input type="text" name="nickname" value="${viewingMember.nickname}" readonly>
				</div>
				<div class="profile-row">
					<label>이메일: </label>
					<input type="email" name="email" value="${viewingMember.email}" readonly>
				</div>
			</form>

			<!-- 🔹 회원이 작성한 게시글 -->
			<h3>회원이 작성한 게시글</h3>
			<div class="list-container">
				<ul>
					<c:forEach var="board" items="${viewingBoards}">
						<li>
							<a href="view?board_id=${board.board_id}">${board.title}</a>
							<button class="delete-btn" onclick="deleteBoard(${board.board_id})">삭제</button>
						</li>
					</c:forEach>
				</ul>
			</div>

			<!-- 🔹 회원이 작성한 댓글 -->
			<h3>회원이 작성한 댓글</h3>
			<div class="list-container">
				<ul>
					<c:forEach var="entry" items="${viewingComments}">
						<c:set var="comment" value="${entry.key}" />
						<c:set var="postTitle" value="${entry.value}" />
						<li>
							<strong>게시글:</strong> <a href="../view?board_id=${comment.board_id}">${postTitle}</a>
							<p>${comment.content}</p>
							<button class="delete-btn" onclick="deleteComment(${comment.comment_id})">삭제</button>
						</li>
					</c:forEach>
				</ul>
			</div>

			<!-- 🔹 회원이 작성한 리뷰 목록 -->
			<h3>회원이 작성한 리뷰</h3>
			<table class="review-list">
				<thead>
					<tr>
						<th>게임명</th>
						<th>평점</th>
						<th>리뷰 내용</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="review" items="${viewingReviews}">
						<tr>
							<td>
								<c:choose>
									<c:when test="${not empty gameNames[review.game_id]}">
										<a href="../gameDetail?game_id=${review.game_id}">
											<c:out value="${gameNames[review.game_id]}" />
										</a>
									</c:when>
									<c:otherwise>
										<span>게임 정보 없음</span>
									</c:otherwise>
								</c:choose>
							</td>
							<td><c:out value="${review.rating}" /></td>
							<td class="review-content"><c:out value="${review.content}" /></td>
							<td>
								<button class="delete-btn" onclick="deleteReview(${review.review_id})">삭제</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<script>
		// fetch() 응답값을 확인하고 성공 시 loction.reload(); 실행
		function deleteBoard(boardId) {
			if (confirm("게시글을 삭제하시겠습니까?")) {
				fetch("BoardDelete", {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: "board_id=" + encodeURIComponent(boardId)
				})
				.then(response => response.json())
				.then(data => {
					if (data.success) {
						alert("게시글이 삭제되었습니다.");
						location.reload();
					} else {
						alert(data.message);
					}
				})
				.catch(error => {
					alert("삭제 중 오류 발생");
					console.error(error);
				});
			}
		}

		function deleteComment(commentId) {
			if (confirm("댓글을 삭제하시겠습니까?")) {
				fetch("CommentDelete", {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: "comment_id=" + encodeURIComponent(commentId)
				})
				.then(response => response.json())
				.then(data => {
					if (data.success) {
						alert("댓글이 삭제되었습니다.");
						location.reload();
					} else {
						alert(data.message);
					}
				})
				.catch(error => {
					alert("삭제 중 오류 발생");
					console.error(error);
				});
			}
		}

		function deleteReview(reviewId) {
			if (confirm("리뷰를 삭제하시겠습니까?")) {
				fetch("reviewDelete", {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: "review_id=" + encodeURIComponent(reviewId)
				})
				.then(response => response.json())
				.then(data => {
					if (data.success) {
						alert("리뷰가 삭제되었습니다.");
						location.reload();
					} else {
						alert(data.message);
					}
				})
				.catch(error => {
					alert("삭제 중 오류 발생");
					console.error(error);
				});
			}
		}

		</script>
	</body>
	</html>
