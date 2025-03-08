package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

@WebServlet("/deleteAccount")
public class DeleteAccountController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser");

		if (loginUser == null) {
			resp.sendRedirect("login");
			return;
		}

		String password = req.getParameter("password");

		MemberDao dao = new MemberDao(getServletContext());
		boolean isPasswordCorrect = dao.checkPassword(loginUser, password);

		if (!isPasswordCorrect) {
			req.setAttribute("error", "비밀번호가 일치하지 않습니다.");
			req.getRequestDispatcher("members/myPage.jsp").forward(req, resp);
			return;
		}

		int result = dao.deleteMember(loginUser);
		dao.close();

		if (result == 1) {
			session.invalidate();
			resp.sendRedirect("main");
		} else {
			req.setAttribute("error", "회원 탈퇴에 실패했습니다.");
			req.getRequestDispatcher("members/myPage.jsp").forward(req, resp);
		}
	}
}
