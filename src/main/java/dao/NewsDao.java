package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import dto.NewsDto;
import util.JDBConnect;

public class NewsDao extends JDBConnect {

	public NewsDao(ServletContext application) {
		super(application); // 부모 클래스 JDBConnect의 DB 연결 사용
	}

	// [1] 뉴스 전체 삭제 (TRUNCATE 사용)
	public void deleteAll() {
		String sql = "TRUNCATE TABLE news";
		try {
			psmt = con.prepareStatement(sql);
			psmt.executeUpdate();
			System.out.println("[NewsDao] 뉴스 데이터 초기화 완료 (TRUNCATE)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// [2] 뉴스 리스트 삽입 (50개)
	public void insertNewsList(List<NewsDto> newsList) {
		String sql = "INSERT INTO news (title, originallink, link, description, pubdate) VALUES (?, ?, ?, ?, ?)";
		try {
			con.setAutoCommit(false); // 트랜잭션 시작
			psmt = con.prepareStatement(sql);
			for (NewsDto news : newsList) {
				psmt.setString(1, news.getTitle());
				psmt.setString(2, news.getOriginallink());
				psmt.setString(3, news.getLink());
				psmt.setString(4, news.getDescription());
				psmt.setTimestamp(5, new Timestamp(news.getPubDate().getTime()));
				psmt.addBatch();
			}
			psmt.executeBatch();
			con.commit();
			System.out.println("[NewsDao] 신규 뉴스 100개 저장 완료");
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	// [3] 뉴스 전체 조회
	public List<NewsDto> getAllNews() {
		List<NewsDto> list = new ArrayList<>();
		String sql = "SELECT * FROM news ORDER BY pubdate DESC";
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				NewsDto news = new NewsDto();
				news.setNews_id(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setOriginallink(rs.getString("originallink"));
				news.setLink(rs.getString("link"));
				news.setDescription(rs.getString("description"));
				news.setPubDate(rs.getTimestamp("pubdate"));
				list.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
