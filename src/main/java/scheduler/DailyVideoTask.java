package scheduler;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import dao.VideoDao;
import dto.VideoDto;
import service.YouTubeAPIService;

public class DailyVideoTask {

	private YouTubeAPIService apiService;
	private VideoDao videoDao;
	private ScheduledExecutorService scheduler;

	// ServletContext 사용 시
	public DailyVideoTask(ServletContext application) {
		// dao with application
		this.videoDao = new VideoDao(application);
		this.apiService = new YouTubeAPIService();
	}

	public void startSchedule() {
		scheduler = Executors.newSingleThreadScheduledExecutor();

		long initialDelay = 0; // 즉시
		long period = 24; // 24시간 주기

		scheduler.scheduleAtFixedRate(() -> {
			try {
				System.out.println("[DailyVideoTask] 시작: 기존 영상 삭제 + 새 영상 Insert");
				// 1) 기존 영상 전부 삭제
				videoDao.deleteAll();

				// 2) 새 50개 가져오기
				List<VideoDto> list = apiService.fetchPopularGameVideos();

				// 3) DB Insert
				if (!list.isEmpty()) {
					videoDao.insertVideoList(list);
					System.out.println("신규 " + list.size() + "개 영상 등록 완료");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, initialDelay, period, TimeUnit.HOURS);
	}

	public void stopSchedule() {
		if (scheduler != null && !scheduler.isShutdown()) {
			scheduler.shutdown();
		}
	}
}
