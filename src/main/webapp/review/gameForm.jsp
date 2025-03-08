<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게임 정보 입력</title>
<link rel="icon" href="main/imagefile/loco.ico" type="image/x-icon">
<link rel="stylesheet" href="review/gameForm.css">
</head>
<body>
	<jsp:include page="../main/navi.jsp" />

	<section class="page-wrap">
		<h1>${game.game_id == 0 ? "게임 추가" : "게임 수정"}</h1>
		<!-- 게임 추가/수정 여부 확인 -->

		<form action="gameSave" method="post">
			<input type="hidden" name="game_id"
				value="${game != null ? game.game_id : ''}">

			<div class="form-group">
				<label>게임명</label> <input type="text" name="game_name"
					value="${game != null ? game.game_name : ''}" required>
			</div>

			<div class="form-group">
				<label>장르</label> <input type="text" name="genre"
					value="${game != null ? game.genre : ''}" required>
			</div>

			<div class="form-group">
				<label>출시일</label> <input type="date" name="release_dt"
					value="${game != null ? game.release_dt : ''}" required>
			</div>

			<div class="form-group">
				<label>게임 설명</label>
				<textarea name="description" required>${game != null ? game.description : ''}</textarea>
			</div>

			<div class="form-group">
				<label>이미지 URL</label> <input type="text" name="game_still_cut"
					value="${game != null ? game.game_still_cut : ''}" required>
			</div>

			<button type="submit">저장</button>
			<button type="button" class="cancel-btn" onclick="history.back()">취소</button>
		</form>
	</section>
</body>
</html>
