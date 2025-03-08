package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GameDao;
import dto.GameDto;

@WebServlet("/gameEdit")
public class GameEditServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gameIdParam = request.getParameter("game_id");
		GameDto game = null;

		// game_id가 있는 경우에만 조회
		if (gameIdParam != null && !gameIdParam.isEmpty()) {
			try {
				int gameId = Integer.parseInt(gameIdParam);
				GameDao gameDao = new GameDao(getServletContext());
				game = gameDao.getGameById(gameId);
				gameDao.close();
			} catch (NumberFormatException e) {
				response.sendRedirect("gameList"); // game_id가 유효하지 않으면 목록으로 이동
				return;
			}
		}

		// game이 null이면 새 게임 추가 폼으로 이동
		request.setAttribute("game", game);
		request.getRequestDispatcher("review/gameForm.jsp").forward(request, response);
	}
}
