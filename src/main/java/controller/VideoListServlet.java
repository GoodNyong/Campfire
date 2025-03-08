package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VideoDao;
import dto.VideoDto;

@WebServlet("/videoList")
public class VideoListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext application = getServletContext();
		VideoDao dao = new VideoDao(application);

		List<VideoDto> list = dao.getAllVideos();
		dao.close();

		request.setAttribute("videoList", list);
		request.getRequestDispatcher("video/videoList.jsp").forward(request, response);
	}
}