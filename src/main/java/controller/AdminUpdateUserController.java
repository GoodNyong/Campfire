package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;

@WebServlet("/updateUser")
public class AdminUpdateUserController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		// 관리자 여부 확인
		Integer role = (Integer) req.getSession().getAttribute("role");
		if (role == null || role != 1) {
			resp.sendRedirect("login");
			return;
		}

		// 수정할 회원 정보 가져오기
		int memberId = Integer.parseInt(req.getParameter("member_id"));
		String newNickname = req.getParameter("nickname");
		String newEmail = req.getParameter("email");
		String newPassword = req.getParameter("password");

		MemberDao dao = new MemberDao(getServletContext());
		MemberDto member = dao.getMemberById(memberId);

		if (member != null) {
			member.setNickname(newNickname);
			member.setEmail(newEmail);

			// 비밀번호 변경 요청이 있는 경우에만 업데이트
			if (newPassword != null && !newPassword.isEmpty()) {
				member.setPassword(newPassword);
			}

			int result = dao.updateMember(member);
			dao.close();

			if (result > 0) {
				resp.getWriter().write("success");
			} else {
				resp.getWriter().write("fail");
			}
		} else {
			resp.getWriter().write("not found");
		}
	}
}
