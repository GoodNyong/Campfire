package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;
import dto.CommentDto;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 추가 처리
		// 🚀 ✅ UTF-8 인코딩 설정
		req.setCharacterEncoding("UTF-8");

		// 🚀 ✅ 예외 방지: 페이지 값이 없으면 기본값 1 설정
		String pageParam = req.getParameter("page");
		int page = (pageParam == null || pageParam.isEmpty()) ? 1 : Integer.parseInt(pageParam);

		// 🚀 댓글 정보 가져오기
		String boardIdParam = req.getParameter("board_id");
		String writerIdParam = req.getParameter("writer_id");
		String idParam = req.getParameter("loginUserId");
		String content = req.getParameter("content");

		// 🚀 ✅ 필수 파라미터가 없는 경우 예외 처리
		if (boardIdParam == null || writerIdParam == null || content == null || content.trim().isEmpty()) {
			resp.getWriter().write("댓글 내용이 없습니다.");
			return;
		}
		// 🚀 `id` 값이 null이거나 빈 값이면 예외 처리
		if (idParam == null || idParam.trim().isEmpty()) {
			resp.getWriter().write("댓글 작성자가 올바르지 않습니다.");
			return;
		}

		// 🚀 ✅ 숫자 파싱 예외 방지
		int boardId = Integer.parseInt(boardIdParam);
		int writerId = Integer.parseInt(writerIdParam);

		// 🚀 DB에 `id` 존재 여부 확인
		CommentDao dao = new CommentDao(req.getServletContext());
		boolean idExists = dao.checkMemberId(idParam);

		if (!idExists) {
			resp.getWriter().write("잘못된 사용자 ID입니다.");
			return;
		}

		// 🚀 ✅ Comment 객체 생성
		CommentDto comment = new CommentDto();
		comment.setBoard_id(boardId);
//		comment.setBoard_id(Integer.parseInt(req.getParameter("board_id")));
		comment.setWriter_id(writerId);
//		comment.setWriter_id(Integer.parseInt(req.getParameter("writer_id")));
		comment.setContent(content);
//		comment.setContent(req.getParameter("content"));
		comment.setId(idParam);

		// 🚀 ✅ DAO 호출
		int result = dao.insertComment(comment);
//		dao.insertComment(comment);

		// 🚀 ✅ 응답 처리
		if (result > 0) {
			// 정상적인 경우, 상세보기 페이지로 이동
			resp.sendRedirect("view?board_id=" + comment.getBoard_id() + "&page=" + page);
		} else {
			// 오류 발생 시, 에러 페이지로 이동
			resp.sendRedirect("error.jsp?msg=댓글 등록 실패");
		}

	}

//	// 🔹 댓글 추가
//	private void addComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		HttpSession session = req.getSession();
//		String loginUser = (String) session.getAttribute("loginUser");
//
//		if (loginUser == null) {
//			resp.sendRedirect("login.jsp");
//			return;
//		}
//
//		int boardId = Integer.parseInt(req.getParameter("board_id"));
//		String content = req.getParameter("content");
//
//		CommentDto comment = new CommentDto();
//		comment.setBoard_id(boardId);
//		comment.setWriter_id(Integer.parseInt(loginUser)); // 세션에 저장된 사용자 ID 사용
//		comment.setContent(content);
//
//		CommentDao dao = new CommentDao(getServletContext());
//		int result = dao.insertComment(comment);
//
//		if (result > 0) {
//			resp.sendRedirect("postDetail?board_id=" + boardId);
//		} else {
//			resp.sendRedirect("error.jsp");
//		}
//	}
//
//	// 🔹 댓글 수정
//	private void updateComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		HttpSession session = req.getSession();
//		String loginUser = (String) session.getAttribute("loginUser");
//
//		if (loginUser == null) {
//			resp.sendRedirect("login.jsp");
//			return;
//		}
//
//		int commentId = Integer.parseInt(req.getParameter("comment_id"));
//		String newContent = req.getParameter("content");
//
//		CommentDao dao = new CommentDao(getServletContext());
//		int result = dao.updateComment(commentId, newContent);
//
//		if (result > 0) {
//			resp.getWriter().write("success");
//		} else {
//			resp.getWriter().write("error");
//		}
//	}
//
//	// 🔹 댓글 삭제
//	private void deleteComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		HttpSession session = req.getSession();
//		String loginUser = (String) session.getAttribute("loginUser");
//
//		if (loginUser == null) {
//			resp.sendRedirect("login.jsp");
//			return;
//		}
//
//		int commentId = Integer.parseInt(req.getParameter("comment_id"));
//
//		CommentDao dao = new CommentDao(getServletContext());
//		int result = dao.deleteComment(commentId);
//
//		if (result > 0) {
//			resp.getWriter().write("success");
//		} else {
//			resp.getWriter().write("error");
//		}
//	}
}
