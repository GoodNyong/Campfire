package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 🔹 세션 무효화 (로그아웃 처리)
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// 🔹 캐시 방지 (뒤로 가기 시 로그인 상태 유지 방지)
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		// 🔹 브라우저 캐시 초기화 (JavaScript로 캐시된 페이지를 무효화)
		response.getWriter().write("<script>");
		response.getWriter().write("window.location.href='./login';");
		response.getWriter().write("</script>");

		// 🔹 메인 페이지로 리디렉션
		response.sendRedirect("./main");

	}
}
