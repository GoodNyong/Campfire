package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDao;
import dao.CommentDao;
import dto.BoardDto;
import dto.CommentDto;

@WebServlet("/view")
public class ViewController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/* HttpSession session = req.getSession(); */
		/* String session_id = (String) session.getAttribute("id"); */

		String boardIdStr = req.getParameter("board_id");
		String pageStr = req.getParameter("page");

		// 🔹 board_id 및 page가 null인지 확인 (예외 방지)
		if (boardIdStr == null || boardIdStr.trim().isEmpty()) {
			System.out.println("❌ [ERROR] 게시글 ID가 전달되지 않았습니다.");
			resp.sendRedirect("BoardList");
			return;
		}

		if (pageStr == null || pageStr.trim().isEmpty()) {
			System.out.println("❌ [ERROR] 페이지 번호가 전달되지 않았습니다.");
			pageStr = "1"; // 기본값 설정
		}

		try {
			int board_id = Integer.parseInt(boardIdStr);
			int page = Integer.parseInt(pageStr);

			BoardDao dao = new BoardDao(getServletContext());
			BoardDto dto = dao.selectView(board_id);

			CommentDao commentDao = new CommentDao(req.getServletContext());
			List<CommentDto> commentList = commentDao.getCommentList(board_id);

			// 🔹 조회수 먼저 증가
			int updateResult = dao.updateViewCnt(board_id);
			// 디버깅 로그 추가
			System.out.println("✅ [DEBUG] 조회수 증가 결과: " + updateResult);

			if (dto != null) {
				req.setAttribute("dto", dto);
				HttpSession session = req.getSession();
				String session_id = (String) session.getAttribute("id");
				req.setAttribute("isAuthor", session_id != null && session_id.equals(dto.getId()));
				System.out.println("✅ [SUCCESS] 게시글 상세 조회 완료: " + board_id);

				req.setAttribute("board_id", board_id);
				req.setAttribute("page", page);
				req.setAttribute("commentList", commentList);
			} else {
				System.out.println("❌ [ERROR] 존재하지 않는 게시글 ID: " + board_id);
				resp.sendRedirect("BoardList");
				return;
			}

			req.getRequestDispatcher("/board/view.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			System.out.println("❌ [ERROR] board_id가 숫자가 아닙니다: " + boardIdStr);
			resp.sendRedirect("BoardList");
		}

//		req.setAttribute("dto", dto);
//		req.setAttribute("session_id", session_id);
//		req.getRequestDispatcher("/board/view.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
