package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import scheduler.DailyNewsTask;
import scheduler.DailyVideoTask;

@WebListener
public class AppContextListener implements ServletContextListener {

	private DailyVideoTask videoTask;
	private DailyNewsTask newsTask;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();

		// 유튜브 스케줄 시작
		videoTask = new DailyVideoTask(application);
		videoTask.startSchedule();
		System.out.println("★ DailyVideoTask 스케줄 시작");

		// 뉴스 스케줄 시작
		newsTask = new DailyNewsTask(application);
		newsTask.startSchedule();
		System.out.println("★ DailyNewsTask 스케줄 시작");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 유튜브 스케줄 정리
		if (videoTask != null) {
			videoTask.stopSchedule();
			System.out.println("★ DailyVideoTask 스케줄 종료");
		}

		// 뉴스 스케줄 정리
		if (newsTask != null) {
			newsTask.stopSchedule();
			System.out.println("★ DailyNewsTask 스케줄 종료");
		}
	}
}
