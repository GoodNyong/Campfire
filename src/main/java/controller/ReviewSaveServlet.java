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

import dao.GameDao;
import dao.MemberDao;
import dao.ReviewDao;
import dto.ReviewDto;
import util.JSFunction;

@WebServlet("/reviewSave")
public class ReviewSaveServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 🔹 로그인한 사용자의 member_id 가져오기
		HttpSession session = request.getSession();
		Integer memberId = (Integer) session.getAttribute("member_id");

		System.out.println("🟢 리뷰 작성 시 로그인된 사용자 ID: " + memberId); // 🔴 디버깅 추가

		if (memberId == null || memberId <= 0) {
			System.out.println("🔴 잘못된 member_id: " + memberId);
			JSFunction.alertBack(response, "로그인 상태가 올바르지 않습니다. 다시 로그인해 주세요.");
			return;
		}

		// 🔹 game_id와 rating 가져오기 (null 검사)
		String gameIdStr = request.getParameter("game_id");
		String ratingStr = request.getParameter("rating");
		String content = request.getParameter("content");

		System.out.println("🟢 game_id: " + gameIdStr);
		System.out.println("🟢 rating: " + ratingStr);
		System.out.println("🟢 content: " + content);

		if (gameIdStr == null || ratingStr == null || content == null || content.isEmpty()) {
			JSFunction.alertBack(response, "잘못된 요청입니다.");
			return;
		}

		int gameId = 0;
		int rating = 0;
		try {
			gameId = Integer.parseInt(gameIdStr);
			rating = Integer.parseInt(ratingStr);
		} catch (NumberFormatException e) {
			JSFunction.alertBack(response, "평점 또는 게임 ID가 잘못되었습니다.");
			return;
		}

		ReviewDto review = new ReviewDto(0, gameId, memberId, rating, content, null);
		ReviewDao reviewDao = new ReviewDao(getServletContext());
		GameDao gameDao = new GameDao(getServletContext());

		int success = reviewDao.insertReview(review);

		if (success > 0) {
			gameDao.updateAvgRating(gameId); // 🔹 평균 평점 업데이트
			JSFunction.alertLocation(response, "리뷰가 등록되었습니다.", "gameDetail?game_id=" + gameId);
		} else {
			JSFunction.alertBack(response, "리뷰 등록에 실패했습니다.");
		}

		reviewDao.close();
		gameDao.close();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("loginUserId");

		if (userId == null) {
			response.sendRedirect("login");
			return;
		}

		// 🔹 세션에서 member_id 가져오기
		Integer memberId = (Integer) session.getAttribute("member_id");

		// 🔹 memberId가 세션에 없으면 DB에서 가져와서 저장
		if (memberId == null) {
			MemberDao memberDao = new MemberDao(getServletContext());
			memberId = memberDao.getMemberIdByUserId(userId);
			session.setAttribute("member_id", memberId); // 세션에 저장
		}

		System.out.println("🟢 [DEBUG] 가져온 memberId: " + memberId); // 디버깅 로그 추가

		ReviewDao reviewDao = new ReviewDao(getServletContext());
		List<ReviewDto> myReviews = reviewDao.getReviewsByUser(memberId); // ✅ int memberId 사용

		// 🔹 게임 이름을 저장할 Map
		Map<Integer, String> gameNames = new HashMap<>();
		for (ReviewDto review : myReviews) {
			gameNames.put(review.getGame_id(), reviewDao.getGameName(review.getGame_id()));
		}

		request.setAttribute("myReviews", myReviews);
		request.setAttribute("gameNames", gameNames); // 🔹 게임 이름 정보 추가
		request.getRequestDispatcher("members/myPage.jsp").forward(request, response);
	}
}
