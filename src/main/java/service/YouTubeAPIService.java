package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dto.VideoDto;

public class YouTubeAPIService {
	// api url 요청 형식 작성
	// 인기순, 카테고리-게임, 지역코드-한국, 가져올 동영상 수 100개
	// api키 입력
	private static final String API_URL = "https://www.googleapis.com/youtube/v3/videos?"
			+ "part=snippet&chart=mostPopular&videoCategoryId=20&regionCode=KR" + "&maxResults=50"
			+ "&key=AIzaSyCm8EpOyOYREb3WKAhd6M299EAX8VXGnkE"; // TODO: 발급받은 API 키

	// 받아온 json데이터를 videoDto로 변환하는 메소드
	public List<VideoDto> fetchPopularGameVideos() {
		List<VideoDto> list = new ArrayList<>(); // 유튜브 데이터를 저장할 리스트
		BufferedReader br = null; // http응답을 읽을 버퍼리더 선언

		try {
			URL url = new URL(API_URL); // 요청을 보낼 url 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // http연결 생성
			conn.setRequestMethod("GET"); // get요청 방식 설정
			conn.setConnectTimeout(8000); // 연결 타임아웃 설정(8초)
			conn.setReadTimeout(8000); // 읽기 타임아웃 설정

			int responseCode = conn.getResponseCode();// 응답 코드 확인
			if (responseCode == 200) { // 200이 정상응답
				br = new BufferedReader(new InputStreamReader(conn.getInputStream())); // 응답 데이터를 읽기 위한 버퍼리더 초기화
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) { // 한 줄씩 읽어 StringBuilder에 추가
					sb.append(line);
				}

				JSONParser parser = new JSONParser(); // json 문자열을 파싱하기 위한 jsonparser 생성
				JSONObject jsonObj = (JSONObject) parser.parse(sb.toString()); // json 문자열을 객체로 변환
				JSONArray items = (JSONArray) jsonObj.get("items"); // "items" 키의 데이터를 JSONArray로 가져옴

				for (Object o : items) { // api 응답 데이터에서 각 동영상 정보를 파싱하여 리스트에 추가
					JSONObject item = (JSONObject) o;
					String videoId = (String) item.get("id"); // 동영상 아이디 가져오기

					JSONObject snippet = (JSONObject) item.get("snippet"); // snippet 객체에서 동영상 관련 정보 추출
					String title = snippet.get("title") != null ? snippet.get("title").toString() : "";
					String channelName = snippet.get("channelTitle") != null ? snippet.get("channelTitle").toString()
							: "";
					String publishedAt = snippet.get("publishedAt") != null ? snippet.get("publishedAt").toString()
							: "";
					// Thumbnails
					JSONObject thumbs = (JSONObject) snippet.get("thumbnails");
					JSONObject high = (JSONObject) thumbs.get("high");
					String thumbnailUrl = high != null ? high.get("url").toString() : "";

					// URL
					String videoUrl = "https://www.youtube.com/watch?v=" + videoId;

					String channelId = snippet.get("channelId") != null ? snippet.get("channelId").toString() : "";
					String channelUrl = "https://www.youtube.com/channel/" + channelId;

					// publishedAt -> Date
					Date pubDate = new Date();
					if (publishedAt.length() >= 19) {
						// e.g. "2025-02-11T09:52:00Z"
						String cutDate = publishedAt.substring(0, 19);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						pubDate = sdf.parse(cutDate);
					}

					// AUTO_INCREMENT PK => video_id=0
					VideoDto dto = new VideoDto();
					dto.setVideo_id(0);
					dto.setTitle(title);
					dto.setThumbnail_url(thumbnailUrl);
					dto.setChannel_name(channelName);
					dto.setPublished_dt(pubDate);
					dto.setVideo_url(videoUrl);
					dto.setChannel_url(channelUrl);

					list.add(dto);
				}
			} else {
				System.out.println("HTTP error code : " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
			}
		}
		return list;
	}
}
