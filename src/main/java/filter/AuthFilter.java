package filter;

//public class AuthFilter implements Filter {
//	public void init(FilterConfig fConfig) throws ServletException {
//	}
//
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		System.out.println("필터 작동");
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		HttpSession session = req.getSession(false);
//
//		// 로그인 상태 체크
//		if (session == null || session.getAttribute("loginUserId") == null) {
//			res.sendRedirect(req.getContextPath() + "/login"); // 로그인 페이지로 이동
//			return;
//		}
//
//		chain.doFilter(request, response);
//	}
//
//	public void destroy() {
//	}
//}
