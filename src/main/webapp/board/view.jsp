<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n");  %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>모닥불 - 게시글 상세보기</title>
	<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
	<!-- ✅ jQuery 추가 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
	  function delCheck(board_id) {
		  let ans = confirm("게시글을 삭제하시겠습니까?");
		  if(ans) location.href = "BoardDelete?board_id="+board_id;
	  }
	  function commentDelete(comment_id) {
		  let ans = confirm("댓글을 삭제할까요?");
		  if(ans) location.href = "CommentDelete?board_id=${dto.board_id}&page=${page}&comment_id="+comment_id;
	  }
	  function showUpdateForm(comment_id) {
          $("#comment-content-" + comment_id).hide();
          $("#comment-box-" + comment_id).show();
      }

	  function cancelUpdate(comment_id) {
          $("#comment-box-" + comment_id).hide();
          $("#comment-content-" + comment_id).show();
      }
	</script>
	<link rel="stylesheet" type="text/css" href="./board/view.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
	<div class="container">
		<div>번호 : ${dto.board_id}</div><br/>
		<div class="board-title">제목 : ${dto.title}</div><br/>
		<div class="board-title">내용 : ${dto.content}</div><br/>
		<div>날짜 : ${dto.reg_date}</div><br/>
		<div>
			<a class="write-btn" href="BoardList">돌아가기</a>
			<!-- 여기를 수정: 세션의 멤버아이디(로그인컨트롤러에서 보냄)과 요청에서 보낸 dto안의 작성자 아이디를 비교 -->
			<c:if test="${sessionScope.member_id == dto.writer_id or sessionScope.role == 1}">
				<c:if test="${sessionScope.member_id == dto.writer_id}">
			    <a class="write-btn" href="BoardUpdate?board_id=${dto.board_id}">수정하기</a> 
			     </c:if>
			    <a class="write-btn" href="javascript:delCheck(${dto.board_id})">삭제하기</a>
		    </c:if>
		</div>
		
		<br/><br/><br/><br/>
		<%-- <h3>댓글(${sessionScope.member_id}/${sessionScope.id})</h3> --%>
		<c:if test="${not empty sessionScope.member_id or sessionScope.role == 1}">
			<form class="comment-del-upd" action="comment" method="post">
		        <input type="hidden" name="page" value="${page}">
		        <input type="hidden" name="board_id" value="${dto.board_id}">
		        <input type="hidden" name="writer_id" value="${sessionScope.member_id}">
		        <input type="hidden" name="loginUserId" value="${sessionScope.loginUserId}">
		        <textarea name="content" rows="3" cols="50" required></textarea><br>
		        <input type="submit" value="댓글 작성">
		    </form>
	    </c:if>
		<h3>댓글 목록</h3>
		<c:forEach var="comment" items="${commentList}">
		    <hr/>
		    <div>
		        <c:set var="content" value="${fn:replace(comment.content, newLine, '<br/>')}"/>
		        <!-- ✅ 기존 댓글 (수정 시 숨김) -->
		        <p id="comment-content-${comment.comment_id}" class="comment-content">${content}</p>
		        <span class="float-text">작성자: ${comment.id}, 작성일: ${comment.reg_date}</span>
				<c:if test="${not empty sessionScope.loginUserId and (sessionScope.loginUserId eq comment.id or sessionScope.role eq 1)}">
					<a class="comment-del-upd" href="javascript:commentDelete(${comment.comment_id})">삭제</a>
				</c:if>
		        <small>
		            <c:if test="${sessionScope.member_id eq comment.writer_id}">
		                <a href="javascript:showUpdateForm(${comment.comment_id})">수정</a>
		            </c:if>
	            </small>
		        <!-- ✅ 수정 폼 (초기에는 숨김) -->
                <div id="comment-box-${comment.comment_id}" class="commentBox">
                    <form method="post" action="CommentUpdate">
                        <textarea name="content" rows="3" cols="50" required>${comment.content}</textarea>
                        <input type="hidden" name="comment_id" value="${comment.comment_id}">
                        <input type="hidden" name="board_id" value="${dto.board_id}">
                        <input type="hidden" name="page" value="${page}">
                        <input type="submit" value="수정 완료">
                        <button type="button" onclick="cancelUpdate(${comment.comment_id})">취소</button>
                    </form>
                </div>
        	</div>
		</c:forEach>
	</div>
</body>
</html>