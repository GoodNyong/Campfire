package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GameDao;
import dao.ReviewDao;
import util.JSFunction;

@WebServlet("/reviewDelete")
public class ReviewDeleteServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 🔹 요청 파라미터 디버깅 로그
		System.out.println("🟢 [DEBUG] 요청된 review_id: " + request.getParameter("review_id"));
		System.out.println("🟢 [DEBUG] 요청된 game_id: " + request.getParameter("game_id"));

		// 🔹 리뷰 ID 가져오기 (null 체크)
		String reviewIdStr = request.getParameter("review_id");
		String gameIdStr = request.getParameter("game_id");

		if (reviewIdStr == null || reviewIdStr.isEmpty()) {
			System.out.println("🔴 [ERROR] review_id가 null 또는 빈 값입니다.");
			JSFunction.alertBack(response, "잘못된 요청입니다. 리뷰 ID가 누락되었습니다.");
			return;
		}

		if (gameIdStr == null || gameIdStr.isEmpty()) {
			System.out.println("🟡 [WARN] game_id가 null 또는 빈 값입니다. 기본값 0 설정.");
			gameIdStr = "0"; // 예외 방지용 기본값 설정
		}

		int reviewId = Integer.parseInt(reviewIdStr);
		int gameId = Integer.parseInt(gameIdStr);

		ReviewDao reviewDao = new ReviewDao(getServletContext());
		GameDao gameDao = new GameDao(getServletContext());

		int success = reviewDao.deleteReview(reviewId);

		if (success > 0) {
			if (gameId != 0) {
				gameDao.updateAvgRating(gameId); // 평균 평점 업데이트
			}

			// 🔹 요청한 페이지(Referer) 가져오기
			String referer = request.getHeader("Referer");

			// 🔹 referer 값이 "gameDetail"을 포함하면 해당 페이지로, 그렇지 않으면 마이페이지로 이동
			String redirectUrl = (referer != null && referer.contains("gameDetail")) ? "gameDetail?game_id=" + gameId
					: "myPage";

			JSFunction.alertLocation(response, "리뷰가 삭제되었습니다.", redirectUrl);
		} else {
			JSFunction.alertBack(response, "리뷰 삭제에 실패했습니다.");
		}

		reviewDao.close();
		gameDao.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String reviewIdStr = req.getParameter("review_id");

		if (reviewIdStr == null || reviewIdStr.isEmpty()) {
			resp.getWriter().write("{\"success\": false, \"message\": \"잘못된 요청입니다.\"}");
			return;
		}

		try {
			int review_id = Integer.parseInt(reviewIdStr);
			ReviewDao reviewDao = new ReviewDao(getServletContext());
			int result = reviewDao.deleteReview(review_id);

			if (result > 0) {
				resp.getWriter().write("{\"success\": true}");
			} else {
				resp.getWriter().write("{\"success\": false, \"message\": \"리뷰 삭제 실패\"}");
			}
		} catch (NumberFormatException e) {
			resp.getWriter().write("{\"success\": false, \"message\": \"유효하지 않은 리뷰 ID\"}");
		}
	}
}
