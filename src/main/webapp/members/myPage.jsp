<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String loginUser = (String) session.getAttribute("loginUserId");
if (loginUser == null) {
	response.sendRedirect("./login");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모닥불 - 마이페이지</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="./members/myPage.css">
</head>
<body>

	<jsp:include page="../main/navi.jsp" />
	<div class="page-wrap">
		<h2 class="admin-title">마이페이지</h2>

		<!-- 회원 정보 수정 폼 -->
		<h3 class="admin-subtitle">회원 정보</h3>
		<form action="editProfile" method="post" class="profile-form" onsubmit="return validateForm();">
		    <div class="profile-row">
		        <label>아이디 : </label>
		        <input type="text" name="id" value="${member.id}" readonly>
		    </div>
		    <div class="profile-row">
		        <label>비밀번호 : </label>
		        <input type="password" name="password" placeholder="현재 비밀번호 입력" required>
		    </div>
		    <div class="profile-row">
		        <label>새 비밀번호 : </label>
		        <input type="password" name="newPassword" id="newPassword" placeholder="변경할 경우 입력">
		    </div>
		    <div class="profile-row">
		        <label>새 비밀번호 확인 : </label>
		        <input type="password" name="confirmPassword" id="confirmPassword" placeholder="새 비밀번호 확인">
		    </div>
		    <div class="profile-row">
		        <label>닉네임 : </label>
		        <input type="text" name="nickname" value="${member.nickname}" required>
		    </div>
		    <div class="profile-row">
		        <label>이메일 : </label>
		        <input type="email" name="email" value="${member.email}" required>
		    </div>
		
		    <!-- 🔹 수정, 회원 탈퇴 버튼 -->
		    <div class="profile-buttons">
		    	<button type="submit" formaction="deleteAccount" class="delete-btn">회원 탈퇴</button>
		        <button type="submit" class="edit-btn">정보 수정</button>
		    </div>
		</form>

		<!-- 내가 쓴 게시글 목록 -->
		<h3 class="admin-trdtitle">내가 쓴 게시글</h3>
		<div class="list-container">
		    <ul>
		        <c:forEach var="board" items="${myBoards}">
		            <li>
		                <a href="view?board_id=${board.board_id}">${board.title}</a> <!-- ✅ URL 수정 -->
		                <div class="button-group">
		                    <a href="BoardUpdate?board_id=${board.board_id}"><button>수정</button></a>
		                    <a href="BoardDelete?board_id=${board.board_id}" onclick="return confirm('정말 삭제하시겠습니까?');"><button>삭제</button></a>
		                </div>
		            </li>
		        </c:forEach>
		    </ul>
		</div>

		<!-- 🔹 내가 쓴 댓글 목록 -->
		<h3 class="admin-trdtitle">내가 쓴 댓글</h3>
		<div class="comment-container">
			<ul>
				<c:forEach var="entry" items="${myComments}">
					<c:set var="comment" value="${entry.key}" />
					<c:set var="postTitle" value="${entry.value}" />
					<li><strong>게시글:</strong> <a
						href="./view?board_id=${comment.board_id}">${postTitle}</a>
						<textarea id="comment_${comment.comment_id}">${comment.content}</textarea>
						<div class="button-group">
							<button onclick="updateComment(${comment.comment_id})">수정</button>
							<a href="deleteComment?comment_id=${comment.comment_id}"><button>삭제</button></a>
						</div></li>
				</c:forEach>
			</ul>
		</div>

		<!-- 🔹 내가 쓴 리뷰 목록 -->
		<h3 class="admin-trdtitle">내가 쓴 리뷰</h3>
		<div class="list-container">
		    <ul>
		        <c:forEach var="review" items="${myReviews}">
		            <li>
		                <!-- ✅ 게임 ID와 함께 게임 이름을 디버깅 출력 -->
		                <strong>게임:</strong> <a href="./gameDetail?game_id=${review.game_id}">
		                    ${gameNames[review.game_id]} <!-- ✅ 여기서 올바르게 게임 이름이 표시됨 -->
		                </a><br>
		
		                <div class="review-container">
		                    <strong>평점:</strong> <input type="number"
		                        id="rating_${review.review_id}" value="${review.rating}" min="1"
		                        max="10"><br>
		                    <textarea id="review_${review.review_id}">${review.content}</textarea>
		                </div>
		
		                <div class="button-group">
		                    <button onclick="updateReview(${review.review_id})">수정</button>
		                    <a href="reviewDelete?review_id=${review.review_id}&game_id=${review.game_id}">
							    <button>삭제</button>
							</a>
		                </div>
		            </li>
		        </c:forEach>
		    </ul>
		</div>
	</div>

	<script>
        function updateComment(commentId) {
            let newContent = document.getElementById("comment_" + commentId).value;
            fetch("updateComment", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "comment_id=" + commentId + "&content=" + encodeURIComponent(newContent)
            }).then(() => location.reload());
        }

        function updateReview(reviewId) {
            let newRating = document.getElementById("rating_" + reviewId).value;
            let newContent = document.getElementById("review_" + reviewId).value;
            fetch("updateReview", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "review_id=" + reviewId + "&rating=" + newRating + "&content=" + encodeURIComponent(newContent)
            }).then(() => location.reload());
        }
        
        function validateForm() {
            let newPassword = document.getElementById("newPassword").value;
            let confirmPassword = document.getElementById("confirmPassword").value;

            if (newPassword !== "" && newPassword !== confirmPassword) {
                alert("새 비밀번호가 일치하지 않습니다.");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
