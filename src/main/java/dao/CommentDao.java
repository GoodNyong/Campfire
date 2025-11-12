package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import dto.CommentDto;
import util.JDBConnect;

public class CommentDao extends JDBConnect {

	public CommentDao(ServletContext application) {
		super(application);
	}

	// 🔹 특정 게시글의 댓글 목록 가져오기
	public List<CommentDto> getCommentsByBoardId(int boardId) {
		List<CommentDto> commentList = new ArrayList<>();
		String sql = "SELECT * FROM comments WHERE board_id = ? ORDER BY reg_date DESC";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, boardId);
			rs = psmt.executeQuery();

			while (rs.next()) {
				CommentDto comment = new CommentDto();
				comment.setComment_id(rs.getInt("comment_id"));
				comment.setBoard_id(rs.getInt("board_id"));
				comment.setWriter_id(rs.getInt("writer_id"));
				comment.setId(rs.getString("id"));
				comment.setContent(rs.getString("content"));
				comment.setReg_date(rs.getTimestamp("reg_date"));

				commentList.add(comment);
			}
		} catch (SQLException e) {
			System.out.println("댓글 목록 조회 중 예외 발생");
			e.printStackTrace();
		}
		return commentList;
	}

	// 🔹 내가 쓴 댓글 조회 (게시글 제목 포함)
	public Map<CommentDto, String> getCommentsByUser(int writerId) {
		Map<CommentDto, String> commentMap = new LinkedHashMap<>();
		String sql = "SELECT c.comment_id, c.board_id, c.writer_id, c.content, c.reg_date, b.title "
				+ "FROM comments c " + "JOIN boards b ON c.board_id = b.board_id " + "WHERE c.writer_id = ? "
				+ "ORDER BY c.reg_date DESC";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, writerId);
			rs = psmt.executeQuery();

			while (rs.next()) {
				CommentDto comment = new CommentDto();
				comment.setComment_id(rs.getInt("comment_id"));
				comment.setBoard_id(rs.getInt("board_id"));
				comment.setWriter_id(rs.getInt("writer_id"));
				comment.setContent(rs.getString("content"));
				comment.setReg_date(rs.getTimestamp("reg_date"));

				String postTitle = rs.getString("title"); // 게시글 제목 가져오기
				commentMap.put(comment, postTitle); // 댓글과 제목을 Map에 저장
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commentMap;
	}

	// 댓글 추가 메소드
	public int insertComment(CommentDto comment) {
		// db에 댓글 추가하는 sql 실행
		int result = 0;

		try {
			// ✅ `members` 테이블에서 `id` 값이 존재하는지 검증 후 삽입
			String query = "INSERT INTO comments (board_id, writer_id, id, content, reg_date) "
					+ "VALUES (?, ?, ?, ?, NOW())";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, comment.getBoard_id());
			psmt.setInt(2, comment.getWriter_id());
			psmt.setString(3, comment.getId());
			psmt.setString(4, comment.getContent());
			// 🚀 ✅ SQL 실행 결과 확인
			result = psmt.executeUpdate();
			System.out.println("✅ 댓글 추가 성공! (결과: " + result + ")");
		} catch (SQLException e) {
			System.out.println("❌ 댓글 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	// 댓글 수정 메소드
	public int updateComment(int commentId, String newContent) {
		int result = 0;
		// content가 null일 경우 빈 문자열로 처리
		if (newContent == null) {
			newContent = "";
		}

		String sql = "UPDATE comments SET content = ?, reg_date = NOW() WHERE comment_id = ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, newContent);
			psmt.setInt(2, commentId);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 🔹 댓글 삭제
	public int deleteComment(int commentId) {
		int result = 0;
		String sql = "DELETE FROM comments WHERE comment_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, commentId);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 댓글 목록 조회 메소드
	public List<CommentDto> getCommentList(int board_id) {
		// 특정 게시글의 댓글 목록을 조회하는 sql실행
		List<CommentDto> list = new ArrayList<>();

		try {
			String query = "SELECT * FROM comments WHERE board_id = ? ORDER BY reg_date DESC";
			psmt = con.prepareStatement(query);
			psmt.setInt(1, board_id);
			rs = psmt.executeQuery();

			while (rs.next()) {
				CommentDto dto = new CommentDto();
				dto.setComment_id(rs.getInt("comment_id"));
				dto.setBoard_id(rs.getInt("board_id"));
				dto.setWriter_id(rs.getInt("writer_id"));
				dto.setId(rs.getString("id"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("댓글 목록 조회 중 예외 발생");
			e.printStackTrace();
		}
		return list;
	}

	public boolean checkMemberId(String id) {
		boolean exists = false;
		try {
			String sql = "SELECT COUNT(*) FROM members WHERE id = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

}
