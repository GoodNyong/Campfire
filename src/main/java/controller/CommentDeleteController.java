package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;

@WebServlet("/CommentDelete")
public class CommentDeleteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 한글 깨짐 방지
			resp.setContentType("text/html; charset=UTF-8");
			resp.setCharacterEncoding("UTF-8");
			// 🔹 null 또는 빈 문자열 체크
			String commentIdStr = req.getParameter("comment_id");
			String boardIdStr = req.getParameter("board_id");
			String pageStr = req.getParameter("page");

			if (commentIdStr == null || commentIdStr.isEmpty() || boardIdStr == null || boardIdStr.isEmpty()) {
				resp.getWriter().write("잘못된 요청입니다.");
				return;
			}

			int comment_id = Integer.parseInt(commentIdStr);
			int board_id = Integer.parseInt(boardIdStr);
			int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1; // ✅ page 기본값 1 설정

			CommentDao dao = new CommentDao(getServletContext());
			int result = dao.deleteComment(comment_id);

			if (result > 0) {
				resp.sendRedirect("view?board_id=" + board_id + "&page=" + page);
			} else {
				resp.getWriter().write("댓글 삭제 실패");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			resp.getWriter().write("유효하지 않은 요청입니다.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String commentIdStr = req.getParameter("comment_id");

		if (commentIdStr == null || commentIdStr.isEmpty()) {
			resp.getWriter().write("{\"success\": false, \"message\": \"잘못된 요청입니다.\"}");
			return;
		}

		try {
			int comment_id = Integer.parseInt(commentIdStr);
			CommentDao dao = new CommentDao(getServletContext());
			int result = dao.deleteComment(comment_id);

			if (result > 0) {
				resp.getWriter().write("{\"success\": true}");
			} else {
				resp.getWriter().write("{\"success\": false, \"message\": \"댓글 삭제 실패\"}");
			}
		} catch (NumberFormatException e) {
			resp.getWriter().write("{\"success\": false, \"message\": \"유효하지 않은 댓글 ID\"}");
		}
	}
}
