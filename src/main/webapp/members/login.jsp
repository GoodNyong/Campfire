<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    // 캐시 비활성화 (뒤로 가기 시 자동 로그인 방지)
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    // 로그아웃 후 login.jsp를 다시 요청하는 경우, `logout.jsp`가 요청되지 않도록 처리
    if (request.getHeader("Referer") != null && request.getHeader("Referer").contains("logout")) {
        response.sendRedirect("./login");
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="members/login.css">
</head>
<body>
<jsp:include page="/main/navi.jsp" />

	<form id="loginform" action="login" method="post">
	
		<div id="title">로그인</div>
		
		<div id="idmsg">
			<input type="text" name="id" id="idField" placeholder="아이디" value="<c:if test='${not empty cookie.savedIds.value}'>${fn:escapeXml(cookie.savedIds.value)}</c:if>" autofocus>
        </div>
        
        <div id="passwordmsg">
			<input type="password" name="password" placeholder="비밀번호">
        </div>
        
        <div id="rem">
            <input type="checkbox" id="idrem" name="rememberId" ${empty cookie.id.value ? "" : "checked"} >
            <label for="idrem">아이디 기억</label>
        </div>
        
		<button id="loginbtn">로그인</button>
		<a href="register"><div id="link">회원가입</div></a><!-- a태그랑 div태그 위치를 맞바꿈, div에도 loginbtn태그 추가 -->
	</form>
</body>
</html>