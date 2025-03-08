package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import dto.VideoDto;
import util.JDBConnect;

public class VideoDao extends JDBConnect {

	// 생성자1 : 기본
	public VideoDao() {
		super(); // JDBConnect() 기본 생성자
	}

	// 생성자2 : ServletContext
	public VideoDao(ServletContext application) {
		super(application);
	}

	// 테이블 전체 삭제 (기존 50개 삭제), ##매일 새로운 영상 50개를 기존 50개를 삭제하고 추가할 예정
	public void deleteAll() {
		String sql = "TRUNCATE TABLE video";
		try {
			psmt = con.prepareStatement(sql);
			psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 50개 영상 Insert ##video_id는 mySQL에서 ai로 추가하므로 여기에서는 입력하지 않음
	public void insertVideoList(List<VideoDto> videoList) {
		String sql = "INSERT INTO video "
				+ "(title, thumbnail_url, channel_name, published_dt, video_url, channel_url) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try {
			con.setAutoCommit(false);

			psmt = con.prepareStatement(sql);

			for (VideoDto v : videoList) {
				psmt.setString(1, v.getTitle());
				psmt.setString(2, v.getThumbnail_url());
				psmt.setString(3, v.getChannel_name());
				psmt.setTimestamp(4, new Timestamp(v.getPublished_dt().getTime()));
				psmt.setString(5, v.getVideo_url());
				psmt.setString(6, v.getChannel_url());
				psmt.addBatch();
			}
			psmt.executeBatch();

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback(); // 오류가 나면 롤백
			} catch (SQLException ex) {
				ex.printStackTrace(); // 그것도 안됐을 경우
			}
		}
	}

	// 전체 목록 SELECT
	public List<VideoDto> getAllVideos() {
		List<VideoDto> list = new ArrayList<>();
		String sql = "SELECT video_id, title, thumbnail_url, channel_name, published_dt, video_url, channel_url "
				+ "FROM video ORDER BY video_id";
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				VideoDto dto = new VideoDto();
				dto.setVideo_id(rs.getInt("video_id"));
				dto.setTitle(rs.getString("title"));
				dto.setThumbnail_url(rs.getString("thumbnail_url"));
				dto.setChannel_name(rs.getString("channel_name"));
				dto.setPublished_dt(rs.getTimestamp("published_dt"));
				dto.setVideo_url(rs.getString("video_url"));
				dto.setChannel_url(rs.getString("channel_url"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
