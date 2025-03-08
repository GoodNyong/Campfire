package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;

@WebServlet("/deleteUser")
public class AdminDeleteUserController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 관리자 여부 확인
		Integer role = (Integer) req.getSession().getAttribute("role");
		if (role == null || role != 1) {
			resp.sendRedirect("login");
			return;
		}

		// 삭제할 회원 ID 가져오기
		int memberId = Integer.parseInt(req.getParameter("member_id"));

		MemberDao dao = new MemberDao(getServletContext());
		int result = dao.deleteMemberById(memberId);
		dao.close();

		if (result > 0) {
			resp.getWriter().write("success");
		} else {
			resp.getWriter().write("fail");
		}
	}
}
