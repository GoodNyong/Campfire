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

/**
 * 메인 페이지 컨트롤러 최신 게임 목록을 조회하여 메인 페이지(main.jsp)에 표시
 */
@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DAO 생성
		GameDao gameDao = new GameDao(getServletContext());

		// 최신 게임 목록 (최대 5개)
		List<GameDto> latestGames = gameDao.getAllGames(0, 5);

		// 데이터 저장 후 DAO 종료
		request.setAttribute("latestGames", latestGames);
		gameDao.close();

		// 메인 페이지로 포워딩
		request.getRequestDispatcher("main/main.jsp").forward(request, response);
	}
}
