package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dto.MemberDto;
import util.JSFunction;

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		// 로그인된 사용자 정보 가져오기
		String loginUser = (String) session.getAttribute("loginUser");
		if (loginUser == null) {
			JSFunction.alertBack(resp, "로그인이 필요합니다.");
			return;
		}

		// 입력값 가져오기
		String currentPassword = req.getParameter("password");
		String newPassword = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmPassword");
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");

		MemberDao memberDao = new MemberDao(getServletContext());
		MemberDto member = memberDao.selectMember(loginUser);

		// 🔹 기존 비밀번호 확인
		if (!member.getPassword().equals(currentPassword)) {
			JSFunction.alertBack(resp, "현재 비밀번호가 일치하지 않습니다.");
			return;
		}

		// 🔹 새 비밀번호 확인
		if (!newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
			JSFunction.alertBack(resp, "새 비밀번호가 일치하지 않습니다.");
			return;
		}

		// 🔹 회원 정보 업데이트
		member.setNickname(nickname);
		member.setEmail(email);
		if (!newPassword.isEmpty()) {
			member.setPassword(newPassword); // 새 비밀번호 적용
		}

		int result = memberDao.updateMember(member);

		if (result > 0) {
			JSFunction.alertLocation(resp, "회원 정보가 수정되었습니다.", "myPage");
		} else {
			JSFunction.alertBack(resp, "회원 정보 수정에 실패했습니다.");
		}

		memberDao.close();
	}
}
