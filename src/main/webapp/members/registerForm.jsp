<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// 캐시 비활성화 (뒤로 가기 방지)
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!-- 회원가입 폼 작성  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="members/registerForm.css">
<style>

</style>
</head>
<body>
	<jsp:include page="/main/navi.jsp" />
	
	<form id="registerForm" action="register" method="post">
		<h2>회원가입</h2>
		<label for="member_id">아이디</label><br> 
        <input type="text" name="id" placeholder="아이디를 입력하세요" autofocus="autofocus" autocomplete="off" required>

		<label for="password">비밀번호</label><br> 
        <input type="password" name="password" placeholder="비밀번호를 입력하세요" autocomplete="off" required> 

        <label for="confirm_password">비밀번호 확인</label><br> 
        <input type="password" name="confirm_password" placeholder="비밀번호를 다시 입력하세요" autocomplete="off" required> 

        <label for="nickname">닉네임</label><br> 
        <input type="text" name="nickname" placeholder="별명을 입력하세요" autocomplete="off" required>

		<label for="email">이메일</label><br> 
        <input type="email" name="email" placeholder="이메일을 입력하세요" autocomplete="off" required>

		<button id="registerbtn" type="submit">회원가입</button>
	</form>
</body>
</html>
</body>
</html>