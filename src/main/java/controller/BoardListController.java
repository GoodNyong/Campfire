package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dto.BoardDto;
import dto.PageHandler;

@WebServlet("/BoardList")
public class BoardListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String command = uri.substring(context.length());

		BoardDao dao = new BoardDao(req.getServletContext());

		// ✅ 검색 및 필터링 관련 파라미터
		String searchField = req.getParameter("searchField"); // 검색 필드(title, content, id)
		String searchWord = req.getParameter("searchWord"); // 검색어
		String category = req.getParameter("category"); // 카테고리 (공지, 특정 게시판 등)
		String pageTemp = req.getParameter("page"); // 페이지 번호

		// ✅ 공지 게시판이면 페이징을 하지 않도록 처리
		boolean isNoticeBoard = "1".equals(category);

		/// 🚀 ✅ 예외 방지 (null 또는 빈 값 처리)
		int page = (pageTemp == null || pageTemp.isEmpty() || isNoticeBoard) ? 1 : Integer.parseInt(pageTemp);

		// ✅ 검색어 및 카테고리 설정
		Map<String, String> map = new HashMap<>();

		// ✅ category 값이 null이면 빈 문자열로 변환하여 명확히 설정
		if (category == null) {
			category = "";
		}

		// ✅ 검색 및 필터링 조건을 Map에 저장
		map.put("category", category); // ✅ 명확한 category 값 전달
		if (searchWord != null && !searchWord.isEmpty()) {
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}

		// ✅ 전체 게시글 개수 조회
		int cnt = dao.selectCount(map);
		PageHandler ph = isNoticeBoard ? null : new PageHandler(cnt, page, searchField, searchWord);

		// ✅ 전체 게시판이면 공지글 제외한 개수로 페이징 적용
		int offset = (page - 1) * (isNoticeBoard ? cnt : 10);
		map.put("offset", String.valueOf(offset));
		map.put("pageSize", "10");

		// 🚀 ✅ 예외 방지 (pageSize가 0일 경우 기본값 10)
//		int offset = (page - 1) * (ph.getPageSize() > 0 ? ph.getPageSize() : 10);

//		map.put("offset", offset + "");
//		map.put("pageSize", ph.getPageSize() + "");
//		map.put("offset", String.valueOf(offset));
//		map.put("pageSize", String.valueOf(ph.getPageSize()));

		// ✅ 디버깅: category 값 출력
		System.out.println("✅ [DEBUG] category: " + category);

		// ✅ 검색된 게시글 목록 조회
		List<BoardDto> blist = dao.selectList(map);
//		System.out.println("✅ [DEBUG] 조회된 게시글 개수: " + blist.size());
		req.setAttribute("blist", blist); // ✅ JSP에 전달
		req.setAttribute("cnt", cnt);
		req.setAttribute("ph", ph);
		req.setAttribute("searchField", searchField);
		req.setAttribute("searchWord", searchWord);
//		System.out.println("✅ [DEBUG] 게시글 목록 전달 완료");

		req.getRequestDispatcher("/board/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
