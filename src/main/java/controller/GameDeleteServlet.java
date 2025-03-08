package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GameDao;
import util.JSFunction;

@WebServlet("/gameDelete")
public class GameDeleteServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int gameId = Integer.parseInt(request.getParameter("game_id"));
		GameDao gameDao = new GameDao(getServletContext());

		int success = gameDao.deleteGame(gameId);
		gameDao.close();

		if (success > 0) {
			JSFunction.alertLocation(response, "게임이 삭제되었습니다.", "gameList");
		} else {
			JSFunction.alertBack(response, "게임 삭제에 실패했습니다.");
		}
	}
}
