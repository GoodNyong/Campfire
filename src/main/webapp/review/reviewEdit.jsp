<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 수정</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" href="review/reviewEdit.css">
</head>
<body>
    <jsp:include page="../main/navi.jsp" />
    <section class="page-wrap">
        <h2>리뷰 수정</h2>
        <form action="updateReview" method="post">
            <input type="hidden" name="review_id" value="${review.review_id}">
            <input type="hidden" name="game_id" value="${game.game_id}">
            
            <label>평점:</label>
            <select name="rating">
                <c:forEach var="i" begin="1" end="5">
                    <option value="${i}" ${review.rating == i ? 'selected' : ''}>${i}점</option>
                </c:forEach>
            </select>
            
            <label>리뷰 내용:</label>
            <textarea name="content" required>${review.content}</textarea>
            
            <button type="submit">저장</button>
            <a href="gameDetail?game_id=${game.game_id}" class="cancel-btn">취소</a>
        </form>
    </section>
</body>
</html>
