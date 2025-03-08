package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDao;
import dto.BoardDto;
import util.JSFunction;

@WebServlet("/Write")
public class WriteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 체크
		HttpSession session = req.getSession();
		if (session.getAttribute("loginUserId") == null || session.getAttribute("loginUserId").toString().isEmpty()) {
			JSFunction.alertLocation(resp, "로그인 후 이용부탁드립니다.", "login");
			return;
		}
		req.getRequestDispatcher("/board/write.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String category = req.getParameter("category");
		int write_id = (int) req.getSession().getAttribute("member_id");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String id = req.getSession().getAttribute("nickname") + "";

		BoardDto b = new BoardDto(category, write_id, id, title, content);
		// System.out.println(b);
		BoardDao dao = new BoardDao(req.getServletContext());
		int res = dao.insertBoard(b);
		// System.out.println("ddddd : " + res);
		if (res == 1) {
			System.out.println(" 글 등록 성공");
			resp.sendRedirect("BoardList"); // JSFunction 대신 직접 리다이렉트
		} else {
			req.setAttribute("errorMessage", "글 등록에 실패했습니다.");
			req.getRequestDispatcher("/board/write.jsp").forward(req, resp);
		}
		dao.close();
	}
}
