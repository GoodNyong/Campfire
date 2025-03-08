package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDao;
import dto.NewsDto;

@WebServlet("/newsList")
public class NewsListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext application = getServletContext();
		NewsDao dao = new NewsDao(application);
		List<NewsDto> list = dao.getAllNews();
		dao.close();

		request.setAttribute("newsList", list);
		request.getRequestDispatcher("news/newsList.jsp").forward(request, response);
	}
}
