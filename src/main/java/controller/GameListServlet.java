package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GameDao;
import dto.GameDto;

@WebServlet("/gameList")
public class GameListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int limit = 11; // 다음 페이지가 있는지 확인하기 위해 11개 가져오기
		int start = (page - 1) * 10;

		GameDao gameDao = new GameDao(getServletContext());

		// 게임 목록 조회 전 모든 게임의 평균 평점 갱신
		gameDao.updateAllAvgRatings();

		// 게임 목록 가져오기
		List<GameDto> gameList = gameDao.getAllGames(start, limit);

		// 다음 페이지가 존재하는지 확인
		boolean hasNextPage = gameList.size() > 10;

		// 실제로는 10개만 보이도록 리스트 조정
		if (hasNextPage) {
			gameList.remove(10);
		}

		request.setAttribute("gameList", gameList);
		request.setAttribute("currentPage", page);
		request.setAttribute("hasNextPage", hasNextPage);

		gameDao.close();
		request.getRequestDispatcher("review/gameList.jsp").forward(request, response);
	}
}
