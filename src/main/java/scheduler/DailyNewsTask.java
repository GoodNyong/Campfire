package scheduler;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import dao.NewsDao;
import dto.NewsDto;
import service.NewsAPIService;

public class DailyNewsTask {
	private NewsAPIService newsService;
	private NewsDao newsDao;
	private ScheduledExecutorService scheduler;

	public DailyNewsTask(ServletContext application) {
		this.newsDao = new NewsDao(application);
		this.newsService = new NewsAPIService();
	}

	// 스케줄 시작 (24시간마다 실행)
	public void startSchedule() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(() -> {
			try {
				System.out.println("[DailyNewsTask] 기존 뉴스 삭제 + 새 뉴스 저장");
				newsDao.deleteAll();
				List<NewsDto> newsList = newsService.fetchGameNews();
				if (!newsList.isEmpty()) {
					newsDao.insertNewsList(newsList);
					System.out.println("신규 " + newsList.size() + "개 뉴스 저장 완료");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 0, 24, TimeUnit.HOURS);
	}

	// 스케줄 정지 (AppContextListener에서 호출)
	public void stopSchedule() {
		if (scheduler != null && !scheduler.isShutdown()) {
			scheduler.shutdown();
			System.out.println("[DailyNewsTask] 스케줄 종료 완료");
		}
	}
}
