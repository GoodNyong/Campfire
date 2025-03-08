package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dto.MemberDto;
import util.JSFunction;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30일 유지
	private static final String DELIMITER = ","; // ✅ 안전한 구분자로 변경 (세미콜론 대신 쉼표 사용)

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 🔹 로그인 페이지 캐시 비활성화
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);

		req.getRequestDispatcher("members/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 🔹 캐시 방지 (로그인 후에도 "뒤로 가기" 방지)
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);

		String id = req.getParameter("id");
		String password = req.getParameter("password");
		String rememberId = req.getParameter("rememberId");
		System.out.println(id + " " + password);

		MemberDao mdao = new MemberDao(req.getServletContext());
		MemberDto mdto = mdao.selectMember(id);
		System.out.println(mdto);

		if (mdto != null) {
			if (mdto.getPassword() == null) {
				System.out.println("🔴 [DEBUG] DB에서 비밀번호를 가져오지 못했습니다. (mdto.getPassword()가 null)");
				JSFunction.alertBack(resp, "로그인 오류: 비밀번호 정보가 없습니다.");
				return;
			}

			if (mdto.getPassword().equals(password)) { // ✅ null 체크 후 비교
				// 로그인 성공 처리
				HttpSession session = req.getSession();
				session.setAttribute("loginUserId", mdto.getId());
				session.setAttribute("nickname", mdto.getNickname());
				session.setAttribute("member_id", mdto.getMember_id());
				session.setAttribute("role", mdto.getRole()); // 🔹 관리자 여부 저장

				System.out.println("✅ [DEBUG] 로그인 성공 - 세션 저장 완료");
				System.out.println("✅ [DEBUG] loginUserId: " + session.getAttribute("loginUserId"));
				System.out.println("✅ [DEBUG] member_id: " + session.getAttribute("member_id"));
				System.out.println("✅ [DEBUG] role: " + session.getAttribute("role"));

				// ✅ 아이디 기억하기 (마지막 로그인한 아이디만 저장)
				if ("on".equals(rememberId)) {
					Cookie idCookie = new Cookie("savedIds", URLEncoder.encode(id, "UTF-8")); // ✅ 인코딩 적용
					idCookie.setMaxAge(COOKIE_MAX_AGE);
					resp.addCookie(idCookie);
				} else {
					// ✅ 체크 해제 시 쿠키 삭제
					Cookie idCookie = new Cookie("savedIds", "");
					idCookie.setMaxAge(0);
					resp.addCookie(idCookie);
				}

				String url = req.getParameter("url");
				if (url == null || url.isEmpty()) {
					url = "./main";
				}
				resp.sendRedirect(url);
				System.out.println("로그인 성공");
			} else {
				System.out.println("🔴 [DEBUG] 비밀번호가 다름");
				req.getRequestDispatcher("members/login.jsp?loginError=1").forward(req, resp);
			}
		} else {
			System.out.println("🔴 [DEBUG] 회원 정보가 없음");
			req.getRequestDispatcher("members/login.jsp?loginError=1").forward(req, resp);
		}

	}
}
