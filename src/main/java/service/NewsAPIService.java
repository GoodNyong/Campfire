package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dto.NewsDto;

public class NewsAPIService {
	// 네이버 API 요청을 위한 클라이언트 ID 및 시크릿 키
	private static final String CLIENT_ID = "9yOKWHxsNeKm0IRToXpl";
	private static final String CLIENT_SECRET = "XmjFYBpjcK";

	// API 호출 및 뉴스 데이터 변환 메소드
	public List<NewsDto> fetchGameNews() {
		List<NewsDto> newsList = new ArrayList<>(); // 뉴스 데이터를 저장할 리스트
		BufferedReader br = null; // HTTP 응답을 읽을 버퍼리더 선언

		try {
			// 검색어 "게임"을 UTF-8로 인코딩하여 API 요청 URL 생성
			String query = URLEncoder.encode("게임", "UTF-8");

			// 네이버 뉴스 검색 API URL (검색어, 출력 개수 50개, 연관도 높은 순 정렬)
			String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + query + "&display=100&sort=sim";

			URL url = new URL(apiURL); // 요청을 보낼 URL 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // HTTP 연결 생성
			conn.setRequestMethod("GET"); // GET 요청 방식 설정
			conn.setRequestProperty("X-Naver-Client-Id", CLIENT_ID); // 클라이언트 ID 설정
			conn.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET); // 클라이언트 시크릿 키 설정
			conn.setConnectTimeout(8000); // 연결 타임아웃 (8초)
			conn.setReadTimeout(8000); // 읽기 타임아웃 (8초)

			// 응답 코드 확인 (200: 정상 응답)
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				// 응답 데이터를 읽기 위한 버퍼리더 초기화
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) { // 한 줄씩 읽어 StringBuilder에 추가
					response.append(line);
				}

				// JSON 데이터를 파싱하여 뉴스 데이터 리스트로 변환
				JSONParser parser = new JSONParser(); // JSON 문자열을 파싱하기 위한 JSONParser 생성
				JSONObject jsonObj = (JSONObject) parser.parse(response.toString()); // JSON 문자열을 JSON 객체로 변환
				JSONArray items = (JSONArray) jsonObj.get("items"); // "items" 키의 데이터를 JSONArray로 가져옴

				// 날짜 형식 변환을 위한 SimpleDateFormat 객체 생성 (네이버 API 날짜 형식)
				SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", java.util.Locale.ENGLISH);

				// API 응답 데이터에서 각 뉴스 정보를 파싱하여 리스트에 추가
				for (Object obj : items) {
					JSONObject item = (JSONObject) obj;
					NewsDto news = new NewsDto(); // 뉴스 DTO 객체 생성

					// 뉴스 제목, 원본 링크, 네이버 뉴스 링크, 설명 가져오기 (NULL 방지)
					news.setTitle(item.get("title") != null ? item.get("title").toString() : "");
					news.setOriginallink(item.get("originallink") != null ? item.get("originallink").toString() : "");
					news.setLink(item.get("link") != null ? item.get("link").toString() : "");
					news.setDescription(item.get("description") != null ? item.get("description").toString() : "");

					// 뉴스 발행일 (pubDate) 변환
					Date pubDate = new Date();
					try {
						if (item.get("pubDate") != null) {
							pubDate = sdf.parse(item.get("pubDate").toString());
						}
					} catch (Exception e) {
						System.out.println("[NewsAPIService] 날짜 변환 오류: " + item.get("pubDate"));
						e.printStackTrace();
					}
					news.setPubDate(pubDate);

					newsList.add(news); // 뉴스 리스트에 추가
				}
			} else {
				System.out.println("HTTP error code : " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace(); // 예외 발생 시 콘솔에 출력
		} finally {
			try {
				if (br != null)
					br.close(); // 버퍼리더 닫기
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return newsList; // 파싱된 뉴스 리스트 반환
	}
}
