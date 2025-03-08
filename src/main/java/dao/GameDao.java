package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import dto.GameDto;
import util.JDBConnect;

public class GameDao extends JDBConnect {

	public GameDao(ServletContext application) {
		super(application);
	}

	// 전체 게임 목록 조회 (페이징 포함)
	public List<GameDto> getAllGames(int start, int limit) {
		List<GameDto> gameList = new ArrayList<>();
		String sql = "SELECT * FROM games ORDER BY release_dt DESC LIMIT ?, ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, limit);
			rs = psmt.executeQuery();

			while (rs.next()) {
				GameDto game = new GameDto();
				game.setGame_id(rs.getInt("game_id"));
				game.setGame_name(rs.getString("game_name"));
				game.setGenre(rs.getString("genre"));
				game.setRelease_dt(rs.getDate("release_dt"));
				game.setDescription(rs.getString("description"));
				game.setGame_still_cut(rs.getString("game_still_cut"));
				game.setAvg_rating(rs.getBigDecimal("avg_rating"));

				gameList.add(game);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameList;
	}

	// 게임 상세 조회
	public GameDto getGameById(int gameId) {
		GameDto game = null;
		String sql = "SELECT * FROM games WHERE game_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, gameId);
			rs = psmt.executeQuery();

			if (rs.next()) {
				game = new GameDto();
				game.setGame_id(rs.getInt("game_id"));
				game.setGame_name(rs.getString("game_name"));
				game.setGenre(rs.getString("genre"));
				game.setRelease_dt(rs.getDate("release_dt"));
				game.setDescription(rs.getString("description"));
				game.setGame_still_cut(rs.getString("game_still_cut"));
				game.setAvg_rating(rs.getBigDecimal("avg_rating"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return game;
	}

	// 게임 추가 또는 수정
	public int saveGame(GameDto game) {
		int result = 0;
		String sql;

		if (game.getGame_id() == 0) { // 신규 추가
			sql = "INSERT INTO games (game_name, genre, release_dt, description, game_still_cut, avg_rating) VALUES (?, ?, ?, ?, ?, 0)";
		} else { // 수정
			sql = "UPDATE games SET game_name=?, genre=?, release_dt=?, description=?, game_still_cut=? WHERE game_id=?";
		}

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, game.getGame_name());
			psmt.setString(2, game.getGenre());
			psmt.setDate(3, new java.sql.Date(game.getRelease_dt().getTime()));
			psmt.setString(4, game.getDescription());
			psmt.setString(5, game.getGame_still_cut());

			if (game.getGame_id() != 0) { // 수정일 경우
				psmt.setInt(6, game.getGame_id());
			}

			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 게임 삭제
	public int deleteGame(int gameId) {
		int result = 0;
		try {
			// ✅ 1. 해당 게임의 리뷰 삭제
			String deleteReviewsSql = "DELETE FROM reviews WHERE game_id = ?";
			psmt = con.prepareStatement(deleteReviewsSql);
			psmt.setInt(1, gameId);
			psmt.executeUpdate(); // 리뷰 삭제 실행
			System.out.println("✅ [SUCCESS] 해당 게임의 리뷰 삭제 완료: game_id = " + gameId);

			// ✅ 2. 게임 삭제
			String deleteGameSql = "DELETE FROM games WHERE game_id = ?";
			psmt = con.prepareStatement(deleteGameSql);
			psmt.setInt(1, gameId);
			result = psmt.executeUpdate();

			if (result > 0) {
				System.out.println("✅ [SUCCESS] 게임 삭제 완료: game_id = " + gameId);
			} else {
				System.out.println("❌ [ERROR] 게임 삭제 실패: game_id = " + gameId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("❌ [ERROR] 게임 삭제 중 오류 발생");
		}
		return result;
	}

	// 평균 평점 업데이트
	public void updateAvgRating(int gameId) {
		String sql = "UPDATE games SET avg_rating = (SELECT IFNULL(AVG(rating), 0) FROM reviews WHERE game_id = ?) WHERE game_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, gameId);
			psmt.setInt(2, gameId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 모든 게임의 평균 평점 업데이트 (게임 목록을 불러올 때 실행)
	public void updateAllAvgRatings() {
		String sql = "UPDATE games g SET avg_rating = (SELECT IFNULL(AVG(r.rating), 0) FROM reviews r WHERE r.game_id = g.game_id)";

		try {
			psmt = con.prepareStatement(sql);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
