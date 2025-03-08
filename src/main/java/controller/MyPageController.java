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
import javax.servlet.http.HttpSession;

import dao.BoardDao;
import dao.CommentDao;
import dao.MemberDao;
import dao.ReviewDao;
import dto.BoardDto;
import dto.CommentDto;
import dto.MemberDto;
import dto.ReviewDto;

@WebServlet("/myPage")
public class MyPageController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("✅ [DEBUG] MyPageController 실행됨!"); // 🔥 실행 여부 확인
		// 🔹 세션에서 로그인한 사용자 ID 가져오기
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUserId");
		// ✅ `loginUserId`가 없으면, `loginUser`도 확인
		if (loginUser == null) {
			loginUser = (String) session.getAttribute("loginUser"); // 🔥 로그인 세션 키가 다를 경우 확인
		}
		Integer role = (Integer) session.getAttribute("role"); // role 정보 가져오기

		// 🚀 디버깅 로그 추가
		System.out.println("✅ [DEBUG] 세션에서 loginUserId 가져옴: " + loginUser);

		if (loginUser == null) {
			System.out.println("❌ [ERROR] 로그인된 사용자가 없습니다. 로그인 페이지로 이동");
			resp.sendRedirect("login"); // 로그인되지 않았다면 로그인 페이지로 이동
			return;
		}
//		// 🔹 관리자인 경우 관리자 페이지로 이동
//		if (role != null && role == 1) {
//			resp.sendRedirect("adminPage");
//			return;
//		}

		// 🔹 DAO 객체 생성
		// 🔹 일반 사용자는 기존 마이페이지 처리
		// 🔹 member_id 가져오기
		Integer memberId = (Integer) session.getAttribute("member_id");
		MemberDao memberDao = new MemberDao(req.getServletContext());
		BoardDao boardDao = new BoardDao(req.getServletContext());
		CommentDao commentDao = new CommentDao(req.getServletContext());
		ReviewDao reviewDao = new ReviewDao(req.getServletContext());

		// 🔹 세션에서 member_id가 없으면 DB에서 가져오기
		if (memberId == null) {
			memberId = memberDao.getMemberIdByUserId(loginUser);
			session.setAttribute("member_id", memberId); // 세션에 저장
		}

		// 🔹 디버깅 로그 추가
		System.out.println("✅ [DEBUG] 조회된 member_id: " + memberId);

		// 🔹 회원 정보 조회
		MemberDto member = memberDao.selectMember(loginUser);
		req.setAttribute("member", member);

		// 🔹 내가 쓴 글 조회 (게시판 기능)
		List<BoardDto> myBoards = boardDao.getBoardsByUser(memberId);
		req.setAttribute("myBoards", myBoards);

		// 🔹 내가 쓴 댓글 가져오기 (Map<CommentDto, String> 형태)
		// 🔹 memberId로 댓글 가져오기
		Map<CommentDto, String> myComments = commentDao.getCommentsByUser(memberId);
		req.setAttribute("myComments", myComments);

		// 🔹 내가 쓴 리뷰 조회
		List<ReviewDto> myReviews = reviewDao.getReviewsByUser(memberId);
		req.setAttribute("myReviews", myReviews);

		// 🔹 게임 이름 맵 생성
		Map<Integer, String> gameNames = new HashMap<>();
		for (ReviewDto review : myReviews) {
			String gameName = reviewDao.getGameName(review.getGame_id());
			gameNames.put(review.getGame_id(), gameName);

//			// ✅ 디버깅 로그 추가
//			System.out.println("✅ 게임 ID: " + review.getGame_id() + ", 게임명: " + gameName);
		}
		req.setAttribute("gameNames", gameNames); // 🔹 JSP에 전달

		// 🔹 마이페이지 JSP로 포워딩
		req.getRequestDispatcher("members/myPage.jsp").forward(req, resp);
	}
}
