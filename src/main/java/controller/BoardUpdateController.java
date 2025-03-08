package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.BoardDto;

@WebServlet("/BoardUpdate")
public class BoardUpdateController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardIdStr = req.getParameter("board_id");

		if (boardIdStr == null || boardIdStr.isEmpty()) {
			System.out.println("❌ [ERROR] 수정할 게시글 ID가 없습니다.");
			resp.sendRedirect("BoardList");
			return;
		}
		try {
			int board_id = Integer.parseInt(boardIdStr);
			BoardDao dao = new BoardDao(getServletContext());
			BoardDto dto = dao.selectView(board_id);

			if (dto == null) {
				System.out.println("❌ [ERROR] 존재하지 않는 게시글 ID: " + board_id);
				resp.sendRedirect("BoardList");
				return;
			}

			req.setAttribute("dto", dto);
			req.getRequestDispatcher("/board/boardUpdate.jsp").forward(req, resp);
		} catch (NumberFormatException e) {
			System.out.println("❌ [ERROR] board_id가 숫자가 아닙니다: " + boardIdStr);
			resp.sendRedirect("BoardList");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int board_id = Integer.parseInt(req.getParameter("board_id"));
		String category = req.getParameter("category");
		String title = req.getParameter("title");
		String content = req.getParameter("content");

		BoardDto dto = new BoardDto();
		dto.setBoard_id(board_id);
		dto.setCategory(category);
		dto.setTitle(title);
		dto.setContent(content);

		BoardDao dao = new BoardDao(req.getServletContext());
		int res = dao.updateBoard(dto);
		if (res == 1) {
			resp.sendRedirect("BoardList"); // JSFunction 대신 직접 리다이렉트
		} else {
			req.setAttribute("errorMessage", "글 수정에 실패했습니다.");
			req.getRequestDispatcher("/board/boardUpdate.jsp").forward(req, resp);
		}
	}
}
