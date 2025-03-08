package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dao.CommentDao;
import dao.MemberDao;
import dao.ReviewDao;
import dto.MemberDto;
import dto.ReviewDto;

@WebServlet("/adminViewUser")
public class AdminViewUserController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("AdminViewUserController 호출 성공");

		// 관리자 여부 확인
		Integer role = (Integer) req.getSession().getAttribute("role");
		if (role == null || role != 1) {
			resp.sendRedirect("login");
			return;
		}

		// 요청된 회원 ID 가져오기
		String memberIdStr = req.getParameter("member_id");
		if (memberIdStr == null || memberIdStr.isEmpty()) {
			req.setAttribute("error", "회원 ID가 전달되지 않았습니다.");
			req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
			return;
		}

		int memberId = Integer.parseInt(memberIdStr); // 🔹 예외 방지: null 체크 후 변환

		// 회원 정보 가져오기
		MemberDao memberDao = new MemberDao(getServletContext());
		MemberDto member = memberDao.getMemberById(memberId);

		if (member == null) {
			req.setAttribute("error", "존재하지 않는 회원입니다.");
			req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
			return;
		}

		// 🔹 게시글, 댓글, 리뷰 정보 조회하여 JSP에 전달
		BoardDao boardDao = new BoardDao(getServletContext());
		CommentDao commentDao = new CommentDao(getServletContext());
		ReviewDao reviewDao = new ReviewDao(getServletContext());

		List<?> viewingBoards = boardDao.getBoardsByUser(memberId);
		Map<?, ?> viewingComments = commentDao.getCommentsByUser(memberId);
		List<ReviewDto> viewingReviews = reviewDao.getReviewsByUser(memberId);

		// 🔹 회원의 리뷰 목록 가져오기 (게임 이름도 함께 가져오기)
		Map<Integer, String> gameNames = new HashMap<>();
		for (ReviewDto review : viewingReviews) {
			gameNames.put(review.getGame_id(), reviewDao.getGameName(review.getGame_id())); // 게임 이름 조회 후 저장
		}

		// ✅ 데이터 JSP로 전달
		req.setAttribute("viewingMember", member);
		req.setAttribute("isAdminView", true);
		req.setAttribute("viewingBoards", viewingBoards);
		req.setAttribute("viewingComments", viewingComments);
		req.setAttribute("gameNames", gameNames); // 🔹 게임 이름 Map 전달
		req.setAttribute("viewingReviews", viewingReviews);

		// ✅ 기존의 `myPage.jsp`를 사용
		req.getRequestDispatcher("./members/adminUserPage.jsp").forward(req, resp);
	}
}
