package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReviewDao;
import dto.ReviewDto;

@WebServlet("/reviewEdit")
public class ReviewEditServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int reviewId = Integer.parseInt(request.getParameter("review_id"));
		ReviewDao reviewDao = new ReviewDao(getServletContext());
		ReviewDto review = reviewDao.getReviewById(reviewId);
		reviewDao.close();

		request.setAttribute("review", review);
		request.getRequestDispatcher("review/reviewEdit.jsp").forward(request, response);
	}
}
