package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import dto.BoardDto;
import util.JDBConnect;

public class BoardDao extends JDBConnect {
	public BoardDao(ServletContext application) {
		super(application);
	}

	public int selectCount(Map<String, String> map) {
		int totalCnt = 0;
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM boards WHERE true");
		List<Object> params = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			String category = map.get("category");
			String searchField = map.get("searchField");
			String searchWord = map.get("searchWord");

			// ✅ 공지 게시판이면 전체 개수를 반환 (페이징 X)
			if ("1".equals(category)) {
				sql = new StringBuilder("SELECT COUNT(*) FROM boards WHERE category = ?");
				params.add("1");
			} else {
				// 카테고리 조건 추가
				if (category != null && !category.isEmpty()) {
					sql.append(" AND category = ?");
					params.add(category);
				}

				// 검색 조건 추가
				if (searchWord != null && !searchWord.isEmpty()) {
					sql.append(" AND ").append(searchField).append(" LIKE ?");
					params.add("%" + searchWord + "%");
				}

				// ✅ 전체 게시판에서는 공지글 개수를 제외하고 카운트
				if (category == null || category.isEmpty()) {
					sql.append(" AND category != '1'");
				}
			}

			pstmt = con.prepareStatement(sql.toString());

			// 파라미터 바인딩
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				totalCnt = resultSet.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("게시물 수 조회 중 에러 발생");
		} finally {
			// 리소스 정리
			try {
				if (resultSet != null) resultSet.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return totalCnt;
	}

	public List<BoardDto> selectList(Map<String, String> map) {
		List<BoardDto> blist = new ArrayList<>();
		try {
			String sql = "";
			boolean hasSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
			String category = map.get("category");

			// ✅ 검색 조건 설정 (제목, 내용, 작성자)
			String searchCondition = "";
			if (hasSearch) {
				searchCondition = " AND " + map.get("searchField") + " LIKE ? ";
			}

			// ✅ 전체 게시판: 공지글 3개 + 일반 게시글 (페이징 적용)
			if (category.isEmpty()) {
				sql = "(SELECT * FROM boards WHERE category = '1' " + (hasSearch ? searchCondition : "")
						+ " ORDER BY board_id DESC LIMIT 3) " + "UNION ALL "
						+ "(SELECT * FROM boards WHERE category != '1' " + (hasSearch ? searchCondition : "")
						+ " ORDER BY board_id DESC LIMIT ?, ?)";
			}
			// ✅ 공지 게시판: 모든 공지글 (최신순) - 페이징 X
			else if ("1".equals(category)) {
				sql = "SELECT * FROM boards WHERE category = '1' " + searchCondition + " ORDER BY board_id DESC";
			}
			// ✅ 특정 게시판: 공지 3개 + 해당 게시판 게시글 (페이징 적용)
			else {
				sql = "(SELECT * FROM boards WHERE category = '1' " + (hasSearch ? searchCondition : "")
						+ " ORDER BY board_id DESC LIMIT 3) " + "UNION ALL "
						+ "(SELECT * FROM boards WHERE category = ? " + (hasSearch ? searchCondition : "")
						+ " ORDER BY board_id DESC LIMIT ?, ?)";
			}

			psmt = con.prepareStatement(sql);
			int paramIndex = 1;

			// ✅ 검색어가 있을 경우 `LIKE` 조건 적용
			if (hasSearch) {
				String searchKeyword = "%" + map.get("searchWord") + "%";
				psmt.setString(paramIndex++, searchKeyword);
				// UNION ALL 적용된 경우 두 번째 검색어도 바인딩 필요
				if (category.isEmpty()) {
					psmt.setString(paramIndex++, searchKeyword);
				}
			}

			// ✅ 특정 게시판일 경우 추가 파라미터 설정
			if (!category.isEmpty() && !"1".equals(category)) {
				psmt.setString(paramIndex++, category);
			}

			// ✅ 페이징 적용 (공지글 제외하고 일반 게시글에만 페이징 적용)
			if (category.isEmpty() || !"1".equals(category)) {
				psmt.setInt(paramIndex++, Integer.parseInt(map.get("offset")));
				psmt.setInt(paramIndex, Integer.parseInt(map.get("pageSize")));
			}

			rs = psmt.executeQuery();

			while (rs.next()) {
				BoardDto b = new BoardDto();
				b.setBoard_id(rs.getInt("board_id"));
				b.setCategory(rs.getString("category"));
				b.setWriter_id(rs.getInt("writer_id"));
				b.setId(rs.getString("id"));
				b.setTitle(rs.getString("title"));
				b.setContent(rs.getString("content"));
				b.setView_count(rs.getInt("view_count"));
				b.setReg_date(rs.getDate("reg_date"));
				blist.add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return blist;
	}

	// 글쓰기
	public int insertBoard(BoardDto b) {
		int res = 0;
		try {
			String sql = "insert into boards ( category, title, content, id, writer_id ) values( ?, ?, ?, ?, ?)";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, b.getCategory());
			psmt.setString(2, b.getTitle());
			psmt.setString(3, b.getContent());
			psmt.setString(4, b.getId());
			psmt.setInt(5, b.getWriter_id());

			res = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	// 업데이트
	public int updateBoard(BoardDto b) {
		int res = 0;
		try {
			String sql = "update boards set title = ? , content = ? , category = ? where board_id = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, b.getTitle());
			psmt.setString(2, b.getContent());
			psmt.setString(3, b.getCategory());
			psmt.setInt(4, b.getBoard_id());
			res = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 삭제
	public int deleteBoard(int board_id) {
		int res = 0;
		try {
			// ✅ 먼저 해당 게시글의 댓글 삭제
			String deleteCommentsSql = "DELETE FROM comments WHERE board_id = ?";
			psmt = con.prepareStatement(deleteCommentsSql);
			psmt.setInt(1, board_id);
			psmt.executeUpdate(); // 댓글 삭제 실행

			// ✅ 게시글 삭제
			String deleteBoardSql = "DELETE FROM boards WHERE board_id = ?";
			psmt = con.prepareStatement(deleteBoardSql);
			psmt.setInt(1, board_id);
			res = psmt.executeUpdate();

			if (res > 0) {
				System.out.println("✅ [SUCCESS] 게시글 삭제 완료: " + board_id);
			} else {
				System.out.println("❌ [ERROR] 게시글 삭제 실패: " + board_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("❌ [ERROR] 게시글 삭제 중 오류 발생");
		}
		return res;
	}

	public int updateViewCnt(int board_id) { // 상세 보기 -> 조회수 1 증가
		int res = 0;
		try {
			String sql = "UPDATE boards SET view_count = view_count + 1 WHERE board_id = ?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, board_id);
			res = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public BoardDto selectView(int board_id) { // 상세보기
		BoardDto b = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			String sql = "SELECT * FROM boards WHERE board_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_id);

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				b = new BoardDto();
				b.setBoard_id(resultSet.getInt("board_id"));
				b.setCategory(resultSet.getString("category"));
				b.setWriter_id(resultSet.getInt("writer_id"));
				b.setId(resultSet.getString("id"));
				b.setTitle(resultSet.getString("title"));
				b.setContent(resultSet.getString("content"));
				b.setView_count(resultSet.getInt("view_count"));
				b.setReg_date(resultSet.getDate("reg_date"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 정리
			try {
				if (resultSet != null) resultSet.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	// 내가 작성한 게시글 조회
	public List<BoardDto> getBoardsByUser(int memberId) {
		List<BoardDto> boardList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			String sql = "SELECT * FROM boards WHERE writer_id = ? ORDER BY reg_date DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				BoardDto board = new BoardDto();
				board.setBoard_id(resultSet.getInt("board_id"));
				board.setCategory(resultSet.getString("category"));
				board.setWriter_id(resultSet.getInt("writer_id"));
				board.setId(resultSet.getString("id"));
				board.setTitle(resultSet.getString("title"));
				board.setContent(resultSet.getString("content"));
				board.setView_count(resultSet.getInt("view_count"));
				board.setReg_date(resultSet.getDate("reg_date"));

				boardList.add(board);
			}
			System.out.println("✅ [DEBUG] 조회된 게시글 개수: " + boardList.size());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("❌ [ERROR] 내가 쓴 게시글 조회 중 오류 발생");
		} finally {
			// 리소스 정리
			try {
				if (resultSet != null) resultSet.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return boardList;
	}

}
