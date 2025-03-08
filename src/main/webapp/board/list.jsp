<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모닥불 - 전처게시판</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="./board/list.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
	<%-- <p>세션: ${sessionScope}</p>
	<p>request: ${requestScope}</p> --%>
    <div class="container">
        <h2 class="board-title">
		    <c:choose>
		        <c:when test="${not empty param.searchWord}">
		            "<c:out value='${param.searchWord}' />" 검색 결과
		        </c:when>
		        <c:when test="${param.category eq '1'}">공지게시판</c:when>
		        <c:when test="${empty param.category}">전체게시판</c:when>
		        <c:otherwise>게시판</c:otherwise>
		    </c:choose>
		</h2>

        
        <!-- ✅ 검색어가 있을 경우 input 필드 유지, 검색된 필드와 검색어 표시 -->
        <div class="search-bar">
		    <form action="BoardList" method="get">
		        <input type="hidden" name="category" value="${param.category}">
		        <input type="text" name="searchWord" placeholder="검색어를 입력해주세요" value="${param.searchWord}">
		        <select name="searchField">
		            <option value="title" ${param.searchField eq 'title' ? 'selected' : ''}>제목</option>
		            <option value="content" ${param.searchField eq 'content' ? 'selected' : ''}>내용</option>
		            <option value="id" ${param.searchField eq 'id' ? 'selected' : ''}>작성자</option>
		        </select>
		        <button type="submit">검색</button>
		    </form>
		</div>

        
        <table class="board-table">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
             <tbody>
                <!-- 게시글이 없는 경우 -->
                <c:if test="${empty blist}">
                    <tr>
                        <td colspan="5" style="text-align:center;">게시글이 없습니다.</td>
                    </tr>
                </c:if>

                <!-- 게시글 목록 출력 -->
                <c:forEach items="${blist}" var="b">
                    <tr class="${b.category eq '1' ? 'notice-row' : ''}">
                        <td>${b.board_id}</td>
                        <td>
                            <a class="none ${b.category eq '1' ? 'notice-title' : ''}" 
                               href="view?board_id=${b.board_id}&page=${ph.page}${ph.query}&category=${param.category}">
                                ${b.title}
                            </a>
                        </td>
                        <td>${b.id}</td>
                        <td>${b.reg_date}</td>
                        <td>${b.view_count}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <%-- 페이지네이션 (공지 게시판에서는 숨김 처리) --%>
		<c:if test="${param.category ne '1'}">
		    <div class="pagination">
		        ${ph.pagingStr("BoardList?")}
		        <form id="actionForm" action="BoardList" method="get">
		            <input type="hidden" name="page" value="${ph.page}">
		            <input type="hidden" name="category" value="${category}">
		            <input type="hidden" name="pageSize" value="${ph.pageSize}">
		            <input type="hidden" name="searchField" value="${param.searchField}">
		            <input type="hidden" name="searchWord" value="${param.searchWord}">
		        </form>
		    </div>
		</c:if>




        <%-- <c:if test="${not empty sessionScope.member_id or sessionScope.role == 1}"> --%>
		    <a class="write-btn" href="./Write">글쓰기</a>
		<%-- </c:if> --%>
        <a class="write-btn" href="./main">돌아가기</a>
    </div>
</body>
</html>
</body>
</html>