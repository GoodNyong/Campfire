package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import dto.ReviewDto;
import util.JDBConnect;

public class ReviewDao extends JDBConnect {

	public ReviewDao(ServletContext application) {
		super(application);
	}

	// 특정 게임의 리뷰 목록 조회 (작성자의 username 추가)
	public Map<ReviewDto, String> getReviewsWithUsernamesByGameId(int gameId) {
		Map<ReviewDto, String> reviewMap = new LinkedHashMap<>();
		String sql = "SELECT r.*, m.id FROM reviews r " + "JOIN members m ON r.member_id = m.member_id "
				+ "WHERE r.game_id = ? ORDER BY r.reg_date DESC";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, gameId);
			rs = psmt.executeQuery();

			while (rs.next()) {
				ReviewDto review = new ReviewDto();
				review.setReview_id(rs.getInt("review_id"));
				review.setGame_id(rs.getInt("game_id"));
				review.setMember_id(rs.getInt("member_id"));
				review.setRating(rs.getInt("rating"));
				review.setContent(rs.getString("content"));
				review.setReg_date(rs.getTimestamp("reg_date"));

				String id = rs.getString("id"); // ✅ id 가져오기
				reviewMap.put(review, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviewMap;
	}

	// 리뷰 추가 메서드
	public int insertReview(ReviewDto review) {
		int result = 0;
		String sql = "INSERT INTO reviews (game_id, member_id, rating, content, reg_date) VALUES (?, ?, ?, ?, NOW())";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, review.getGame_id());
			psmt.setInt(2, review.getMember_id());
			psmt.setInt(3, review.getRating());
			psmt.setString(4, review.getContent());

			System.out.println("🟢 실행될 SQL: " + psmt.toString()); // ✅ 실행되는 SQL을 로그로 출력
			result = psmt.executeUpdate();
			System.out.println("✅ 리뷰 삽입 결과: " + result); // ✅ 실행 결과 로그
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 리뷰 삭제
	public int deleteReview(int reviewId) {
		int result = 0;
		String sql = "DELETE FROM reviews WHERE review_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, reviewId);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<ReviewDto> getReviewsByUser(int memberId) {
		List<ReviewDto> reviewList = new ArrayList<>();
		Map<Integer, String> gameNames = new HashMap<>(); // 🔹 게임 이름을 저장할 맵

		try {
			String sql = "SELECT r.review_id, r.game_id, r.rating, r.content, r.member_id, r.reg_date, g.game_name "
					+ "FROM reviews r JOIN games g ON r.game_id = g.game_id WHERE r.member_id = ?";
			// 추가

			psmt = con.prepareStatement(sql);
			psmt.setInt(1, memberId); // ✅ userId 대신 member_id 사용
			rs = psmt.executeQuery();

			System.out.println("🟢 [DEBUG] 조회된 리뷰 리스트:");

			while (rs.next()) {
				// 🔹 ReviewDto 객체 생성
				ReviewDto review = new ReviewDto();
				review.setReview_id(rs.getInt("review_id"));
				review.setGame_id(rs.getInt("game_id"));
				review.setMember_id(rs.getInt("member_id"));
				review.setRating(rs.getInt("rating"));
				review.setContent(rs.getString("content"));
				review.setReg_date(rs.getTimestamp("reg_date"));
				reviewList.add(review);

				// 🔹 게임 이름을 따로 저장 (game_id 기반)
				gameNames.put(rs.getInt("game_id"), rs.getString("game_name")); // 🔹 게임 이름 저장

				// 🔹 디버깅 로그 추가
				System.out.println("✅ 리뷰 ID: " + review.getReview_id() + ", 게임: " + rs.getString("game_name") + ", 평점: "
						+ review.getRating() + ", 내용: " + review.getContent());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 🔹 게임 이름 정보를 함께 반환하기 위해 리스트를 포함한 Map을 반환하는 방법도 가능
		return reviewList;
	}

	// 특정 game_id에 대한 게임 이름 조회
	public String getGameName(int gameId) {
		String gameName = "";
		try {
			String sql = "SELECT game_name FROM games WHERE game_id = ?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, gameId);
			rs = psmt.executeQuery();

			if (rs.next()) {
				gameName = rs.getString("game_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameName;
	}

	// 리뷰 수정 메서드 추가
	public int updateReview(int reviewId, int rating, String content) {
		int result = 0;
		String sql = "UPDATE reviews SET rating = ?, content = ? WHERE review_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, rating);
			psmt.setString(2, content);
			psmt.setInt(3, reviewId);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 리뷰 ID를 기반으로 game_id 조회
	public int getGameIdByReviewId(int reviewId) {
		int gameId = -1;
		String sql = "SELECT game_id FROM reviews WHERE review_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, reviewId);
			rs = psmt.executeQuery();
			if (rs.next()) {
				gameId = rs.getInt("game_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameId;
	}

	// 특정 리뷰 정보 가져오기
	public ReviewDto getReviewById(int reviewId) {
		ReviewDto review = null;
		String sql = "SELECT * FROM reviews WHERE review_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, reviewId);
			rs = psmt.executeQuery();

			if (rs.next()) {
				review = new ReviewDto();
				review.setReview_id(rs.getInt("review_id"));
				review.setGame_id(rs.getInt("game_id"));
				review.setMember_id(rs.getInt("member_id"));
				review.setRating(rs.getInt("rating"));
				review.setContent(rs.getString("content"));
				review.setReg_date(rs.getTimestamp("reg_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return review;
	}

}
