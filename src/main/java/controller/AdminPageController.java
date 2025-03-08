package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dto.MemberDto;

@WebServlet("/adminPage")
public class AdminPageController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("adminPage로 AdminPageController 불러와짐");
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUserId");
		Integer role = (Integer) session.getAttribute("role");
		System.out.println("loginUser : " + loginUser + ", role : " + role);

		// 🔹 관리자 여부 확인
		if (loginUser == null || role == null || role != 1) {
			System.out.println("아이디 또는 role이 null 이거나 role이 1(관리자)이 아님");
			resp.sendRedirect("./login");
			return;
		}

		MemberDao dao = new MemberDao(req.getServletContext());

		// 🔹 관리자 정보 가져오기
		MemberDto adminInfo = dao.getMemberById(loginUser);
		req.setAttribute("adminInfo", adminInfo);

		// 🔹 회원 목록 가져오기
		List<MemberDto> userList = dao.getAllMembers();
		req.setAttribute("userList", userList);

		req.getRequestDispatcher("/members/adminPage.jsp").forward(req, resp);
	}
}
