package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;

@WebServlet("/BoardDelete")
public class BoardDeleteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardIdStr = req.getParameter("board_id");

		try {
			int board_id = Integer.parseInt(boardIdStr);
			BoardDao dao = new BoardDao(getServletContext());
			int res = dao.deleteBoard(board_id);

			if (res > 0) {
				System.out.println("✅ [SUCCESS] 게시글 삭제 완료: " + board_id);
			} else {
				System.out.println("❌ [ERROR] 게시글 삭제 실패: " + board_id);
			}
		} catch (NumberFormatException e) {
			System.out.println("❌ [ERROR] board_id가 숫자가 아닙니다: " + boardIdStr);
		}

		// 삭제 후 게시판 목록으로 이동
		resp.sendRedirect("BoardList");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardIdStr = req.getParameter("board_id");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		try {
			int board_id = Integer.parseInt(boardIdStr);
			BoardDao dao = new BoardDao(getServletContext());
			int res = dao.deleteBoard(board_id);

			if (res > 0) {
				resp.getWriter().write("{\"success\": true}");
			} else {
				resp.getWriter().write("{\"success\": false, \"message\": \"게시글 삭제 실패\"}");
			}
		} catch (NumberFormatException e) {
			resp.getWriter().write("{\"success\": false, \"message\": \"유효하지 않은 게시글 ID\"}");
		}
	}
}
