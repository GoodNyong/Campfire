<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="./board/boardUpdate.css">
  <script>
    function fCheck() {
    	let title = formCheck.title.value;
    	let content = formCheck.content.value;
    	
    	if(title == "") {
    		alert("제목을 입력하세요");
    		formCheck.title.focus();
    	}
    	else if(content == "") {
    		alert("내용을 입력하세요");
    		formCheck.content.focus();
    	}
    	else {
    		formCheck.submit();
    	}
    }
  </script>
  
</head>
<body>
	<jsp:include page="../main/navi.jsp" />
    <form name="formCheck" class="container" method="post" action="BoardUpdate">
        <table class="board-table">
            <thead>
                <tr>
                    <th colspan="2">게시판 수정하기</th>
                </tr>
            </thead>
            <tbody>
                <!-- 여기에 게시글 목록을 동적으로 추가 -->
                <tr>
                	<td class="list-title"><input type="text" placeholder="글 제목" name="title" value="${dto.title}" maxlength="50">
                		<select name="category">
		                	<option value=1 selelcted>전체
		                	<option value=2>게임
		                	<option value=3>공지
		                	<!-- 뉴스, 영상은 자동 업로드(수정불가) -->	
		                	<!-- 리뷰는 board로 다루지 않으므로 삭제 -->	
                		</select>
                	</td>
                </tr>
                <tr>	
                	<td><textarea class="list-detail" placeholder="글 내용" name="content" style="height: 350px;" >${dto.content}</textarea></td>
                </tr>
            </tbody>
        </table>
    	
        <!-- <input type="submit" class="write-btn" value="글쓰기"> -->
        <input type="button" onclick="fCheck()" class="write-btn" value="수정하기">
        <input type="hidden" name="board_id" value="${dto.board_id }"/>
    </form>
</body>
</html>