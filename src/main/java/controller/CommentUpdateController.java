package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;

@WebServlet("/CommentUpdate")
public class CommentUpdateController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 🔹 빈 문자열 또는 null 값 방지
			String commentIdStr = req.getParameter("comment_id");
			String boardIdStr = req.getParameter("board_id");
			String pageStr = req.getParameter("page");
			String content = req.getParameter("content");

			if (commentIdStr == null || commentIdStr.isEmpty()) {
				resp.getWriter().write("수정 실패: comment_id가 전달되지 않았습니다.");
				return;
			}

			// 🔹 파라미터가 null 또는 빈 문자열이면 기본값 설정
			int comment_id = (commentIdStr != null && !commentIdStr.isEmpty()) ? Integer.parseInt(commentIdStr) : 0;
			int board_id = (boardIdStr != null && !boardIdStr.isEmpty()) ? Integer.parseInt(boardIdStr) : 0;
			int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;

			// 🔹 유효한 댓글 ID가 없으면 예외 발생 방지
			if (comment_id == 0 || board_id == 0) {
				resp.getWriter().write("댓글 수정 실패: 잘못된 요청입니다.");
				return;
			}

			// DAO 생성 및 업데이트 실행
			CommentDao dao = new CommentDao(getServletContext());
			int result = dao.updateComment(comment_id, content);

			// 🔹 댓글 수정 후 리다이렉트
			if (result > 0) {
				resp.sendRedirect("view?board_id=" + board_id + "&page=" + page);
				resp.getWriter().write("success"); // ✅ 성공 시 "success" 반환
			} else {
				resp.getWriter().write("댓글 수정 실패");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			resp.getWriter().write("댓글 수정 실패: 유효하지 않은 숫자 형식입니다.");
		}
	}
}
