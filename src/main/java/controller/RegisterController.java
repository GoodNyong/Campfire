package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 폼 페이지로 이동
		req.getRequestDispatcher("members/registerForm.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 폼에서 입력한 데이터 가져오기
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");

		// 회원가입 날짜를 변수에 저장
		Timestamp reg_date = Timestamp.valueOf(LocalDateTime.now());

		// MemberDao 객체 생성 및 회원 추가
		MemberDao dao = new MemberDao(req.getServletContext());
		MemberDto m = new MemberDto(0, id, password, nickname, email, reg_date, 0);
		System.out.println("회원 정보: " + m);

		int res = dao.insertMember(m);
		dao.close();

		if (res == 1) {
			// 회원가입 성공 → 메인 페이지로 이동
			resp.sendRedirect("./main");
		} else {
			// 회원가입 실패 → 회원가입 폼 페이지로 다시 이동 (오류 메시지 전달)
			req.setAttribute("m", m);
			req.getRequestDispatcher("members/registerForm.jsp?msg=" + URLEncoder.encode("회원가입에 실패했습니다.", "utf-8"))
					.forward(req, resp);
		}
	}
}
