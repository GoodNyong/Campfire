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

@WebServlet("/updateReview")
public class ReviewUpdateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 요청 파라미터 가져오기
		int reviewId = Integer.parseInt(request.getParameter("review_id"));
		int rating = Integer.parseInt(request.getParameter("rating"));
		String content = request.getParameter("content");

		ReviewDao reviewDao = new ReviewDao(getServletContext());
		GameDao gameDao = new GameDao(getServletContext());

		// 리뷰 수정 실행
		int success = reviewDao.updateReview(reviewId, rating, content);

		if (success > 0) {
			// 평점 갱신
			int gameId = reviewDao.getGameIdByReviewId(reviewId);
			gameDao.updateAvgRating(gameId);

			JSFunction.alertLocation(response, "리뷰가 수정되었습니다.", "gameDetail?game_id=" + gameId);
		} else {
			JSFunction.alertBack(response, "리뷰 수정에 실패했습니다.");
		}

		reviewDao.close();
		gameDao.close();
	}
}
