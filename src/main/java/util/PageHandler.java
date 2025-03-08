package util;

public class PageHandler {
	private String searchField;
	private String searchWord;
	private int totalCnt;
	private int pageSize;
	private int naviSize;
	private int totalPage;
	private int page;
	private int beginPage;
	private int endPage;
	private boolean showPrev;
	private boolean showNext;

	public PageHandler(int totalCnt, int pageSize, int naviSize, int page, String searchField, String searchWord) {
		this.page = page;
		System.out.println(totalCnt);
		this.totalCnt = totalCnt;
		this.pageSize = pageSize;
		this.naviSize = naviSize;
		this.searchField = searchField;
		this.searchWord = searchWord;

		totalPage = (int) Math.ceil(totalCnt / (double) pageSize);
		beginPage = (page - 1) / naviSize * naviSize + 1;
		endPage = Math.min(beginPage + naviSize - 1, totalPage);
		showPrev = beginPage != 1;
		showNext = endPage != totalPage;
	}

	public String pagingStr(String reqUrl) {
		String query = "";
		if (searchField != null && !"null".equals(searchField) && searchWord != null && !"null".equals(searchWord)) {
			query = "&searchWord=" + searchWord + "&searchField=" + searchField;
		}

		// 단계 4 : 이전 페이지 블록 바로가기 출력
		String pagingStr = "";
		if (beginPage != 1) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=1" + query + ">&laquo;</a>";
			pagingStr += "&nbsp";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + (beginPage - 1) + query + ">&lt;</a>";
		}
		int pageTemp = beginPage;
		// 단계 5 : 각 페이지 번호 출력
		int blockCount = 1;
		while (blockCount <= naviSize && pageTemp <= totalPage) {
			if (pageTemp == page) {
				pagingStr += "&nbsp;<a class='check' href='" + reqUrl + "?pageNum=" + pageTemp + query + "'>" + pageTemp
						+ "</a>&nbsp;";
			} else {
				pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp + query + "'>" + pageTemp
						+ "</a>&nbsp;";
			}
			pageTemp++;
			blockCount++;
		}

		// 단계 6 : 다음 페이지 블록 바로가기 출겨
		if (pageTemp <= totalPage) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + pageTemp + query + "'>&gt;</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPage + query + "'>&raquo;</a>";
		}
		return pagingStr;
	}
}
