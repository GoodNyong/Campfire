package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GameDao;
import dao.ReviewDao;
import dto.GameDto;
import dto.ReviewDto;

@WebServlet("/gameDetail")
public class GameDetailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int gameId = Integer.parseInt(request.getParameter("game_id"));
		ServletContext application = getServletContext();
		GameDao gameDao = new GameDao(application);
		ReviewDao reviewDao = new ReviewDao(application);

		// 게임의 평균 평점 갱신
		gameDao.updateAvgRating(gameId);

		// 게임 정보 가져오기
		GameDto game = gameDao.getGameById(gameId);
		Map<ReviewDto, String> reviewsWithUsernames = reviewDao.getReviewsWithUsernamesByGameId(gameId);

		gameDao.close();
		reviewDao.close();

		request.setAttribute("game", game);
		request.setAttribute("reviewsWithUsernames", reviewsWithUsernames);
		request.getRequestDispatcher("review/gameDetail.jsp").forward(request, response);
	}
}
