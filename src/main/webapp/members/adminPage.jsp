<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String loginUser = (String) session.getAttribute("loginUserId");
Integer role = (Integer) session.getAttribute("role");

// 🔹 관리자 계정이 아니라면 접근 불가
if (loginUser == null || role == null || role != 1) {
	response.sendRedirect("./login");
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>모닥불 - 관리자 페이지</title>
	<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="./members/adminPage.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
	<%-- <p>session: ${sessionScope }</p> --%>
	<div class="page-wrap">
		<h2 class="admin-title">관리자 페이지</h2>

		<!-- 🔹 관리자 정보 조회 -->
		<h3 class="admin-subtitle">관리자 정보</h3>
		<form action="editProfile" method="post" class="profile-form" onsubmit="return validateForm();">
		    <div class="profile-row">
		        <label>아이디 : </label>
		        <input type="text" name="id" value="${adminInfo.id}" readonly>
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
		        <input type="text" name="nickname" value="${adminInfo.nickname}" required>
		    </div>
		    <div class="profile-row">
		        <label>이메일 : </label>
		        <input type="email" name="email" value="${adminInfo.email}" required>
		    </div>
		    <div class="profile-buttons">
		        <button type="submit" class="edit-btn">정보 수정</button>
		    </div>
		</form>

		<!-- 🔹 회원 목록 -->
		<h3 class="admin-trdtitle">회원 목록</h3>
		<div class="list-container">
		    <table class="admin-table">
		        <thead>
		            <tr>
		                <th>회원 ID</th>
		                <th>아이디</th>
		                <th>닉네임</th>
		                <th>이메일</th>
		                <th>가입일</th>
		                <th>관리</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach var="user" items="${userList}">
		                <tr>
		                    <td>${user.member_id}</td>
		                    <td>${user.id}</td>
		                    <td><input type="text" id="nickname_${user.member_id}" value="${user.nickname}"></td>
		                    <td><input type="email" id="email_${user.member_id}" value="${user.email}"></td>
		                    <td>${user.reg_date}</td>
		                    <td>
		                        <button class="edit-btn" onclick="updateUser(${user.member_id})">수정</button>
		                        <button class="delete-btn" onclick="deleteUser(${user.member_id})">삭제</button>
		                        <!-- ✅ 특정 회원의 마이페이지 접근 버튼 -->
		                          <button class="view-btn" onclick="location.href='adminViewUser?member_id=${user.member_id}'">마이페이지</button>
		                    </td>
		                </tr>
		            </c:forEach>
		        </tbody>
		    </table>
		</div>
	</div>
	<script type="text/javascript">
	// 🔹 회원 정보 수정 요청
	function updateUser(memberId) {
	    let newNickname = document.getElementById("nickname_" + memberId).value;
	    let newEmail = document.getElementById("email_" + memberId).value;
	    let newPassword = document.getElementById("password_" + memberId)?.value || ""; // 비밀번호 변경 가능하도록 추가

	    fetch("updateUser", {
	        method: "POST",
	        headers: { "Content-Type": "application/x-www-form-urlencoded" },
	        body: "member_id=" + memberId + "&nickname=" + encodeURIComponent(newNickname) +
	              "&email=" + encodeURIComponent(newEmail) + "&password=" + encodeURIComponent(newPassword)
	    }).then(response => response.text()).then(result => {
	        if (result.trim() === "success") {
	            alert("회원 정보가 수정되었습니다.");
	            location.reload();
	        } else {
	            alert("회원 정보 수정에 실패했습니다.");
	        }
	    }).catch(error => console.error("Error:", error));
	}

	// 🔹 회원 삭제 요청
	function deleteUser(memberId) {
	    if (confirm("정말 삭제하시겠습니까?")) {
	        fetch("deleteUser", {
	            method: "POST",
	            headers: { "Content-Type": "application/x-www-form-urlencoded" },
	            body: "member_id=" + memberId
	        }).then(response => response.text()).then(result => {
	            if (result.trim() === "success") {
	                alert("회원이 삭제되었습니다.");
	                location.reload();
	            } else {
	                alert("회원 삭제에 실패했습니다.");
	            }
	        }).catch(error => console.error("Error:", error));
	    }
	}

	</script>
</body>
</html>
