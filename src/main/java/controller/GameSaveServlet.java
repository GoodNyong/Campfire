package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GameDao;
import dto.GameDto;
import util.JSFunction;

@WebServlet("/gameSave")
public class GameSaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int gameId = 0;
        if (request.getParameter("game_id") != null && !request.getParameter("game_id").isEmpty()) {
            gameId = Integer.parseInt(request.getParameter("game_id"));
        }

        // 폼에서 받은 데이터
        String gameName = request.getParameter("game_name");
        String genre = request.getParameter("genre");
        Date releaseDt = Date.valueOf(request.getParameter("release_dt"));
        String description = request.getParameter("description");
        String gameStillCut = request.getParameter("game_still_cut");

        GameDto game = new GameDto(gameId, gameName, genre, releaseDt, description, gameStillCut, null);
        GameDao gameDao = new GameDao(getServletContext());

        int success = gameDao.saveGame(game);
        gameDao.close();

        if (success > 0) {
            JSFunction.alertLocation(response, "게임 정보가 저장되었습니다.", "gameList");
        } else {
            JSFunction.alertBack(response, "게임 저장에 실패했습니다.");
        }
    }
}
