package dto;

public class PageHandler {
	private int totalCnt; // 총 게시물 개수
	private int pageSize = 10; // 한 페이지당 몇 개의 게시물을 보여줄지 (기본값 10)
	private int naviSize = 10; // 페이지 네비게이션 크기 (1~10)
	private int totalPage; // 총 페이지 수
	private int page; // 현재 페이지
	private int beginPage; // 네비게이션 시작 페이지
	private int endPage; // 네비게이션 끝 페이지
	private boolean showPrev; // 이전 페이지 존재 여부
	private boolean showNext; // 다음 페이지 존재 여부

	private String searchField; // 검색 분야
	private String searchWord; // 검색어
	// ✅ 초기화 수정
	private String query = "";

	// ✅ 기존 생성자 수정 (검색어 있을 경우 query 추가)
	public PageHandler(int totalCnt, int page, String searchField, String searchWord) {
		this.page = (page < 1) ? 1 : page; // 페이지 기본값 1
		this.totalCnt = totalCnt;
		this.searchField = searchField;
		this.searchWord = searchWord;

		// ✅ 검색 조건이 있으면 query 문자열 생성
		if (searchWord != null && !searchWord.isEmpty()) {
			this.query = "&searchField=" + searchField + "&searchWord=" + searchWord;
		}

		// ✅ 총 페이지 수 계산 (최소 1 보장)
		this.totalPage = Math.max(1, (int) Math.ceil((double) totalCnt / pageSize));

		// ✅ 페이지 네비게이션 범위 계산
		this.beginPage = ((page - 1) / naviSize) * naviSize + 1;
		this.endPage = Math.min(beginPage + naviSize - 1, totalPage);

		// ✅ 이전/다음 버튼 표시 여부
		this.showPrev = (beginPage > 1);
		this.showNext = (endPage < totalPage);
	}

	// ✅ 페이지네이션 HTML 생성
	public String pagingStr(String reqUrl) {
		StringBuilder pagingStr = new StringBuilder();
		String safeQuery = (query != null) ? query : ""; // ✅ null 방지

		// 🚀 ✅ "맨 처음 (≪)" 버튼 (1페이지가 아니면 표시)
		if (page > 1) {
			pagingStr.append("<a href='").append(reqUrl).append("page=1").append(safeQuery).append("'>&laquo;</a> ");
		}

		// 🚀 ✅ "이전 (＜)" 버튼 (1페이지가 아니면 표시)
		if (page > 1) {
			pagingStr.append("<a href='").append(reqUrl).append("page=").append(page - 1).append(safeQuery)
					.append("'>&lt;</a> ");
		}

		// 🚀 ✅ 페이지 번호 출력
		for (int i = beginPage; i <= endPage; i++) {
			if (i == page) {
				pagingStr.append("<strong> " + i + " </strong> ");
			} else {
				pagingStr.append("<a href='").append(reqUrl).append("page=").append(i).append(safeQuery).append("'>")
						.append(i).append("</a> ");
			}
		}

		// 🚀 ✅ "다음 (＞)" 버튼 (마지막 페이지가 아니면 표시)
		if (page < totalPage) {
			pagingStr.append("<a href='").append(reqUrl).append("page=").append(page + 1).append(safeQuery)
					.append("'>&gt;</a> ");
		}

		// 🚀 ✅ "맨 끝 (≫)" 버튼 (마지막 페이지가 아니면 표시)
		if (page < totalPage) {
			pagingStr.append("<a href='").append(reqUrl).append("page=").append(totalPage).append(safeQuery)
					.append("'>&raquo;</a>");
		}

		return pagingStr.toString();
	}

	// ✅ query 필드의 Getter 추가 (JSP에서 ${ph.query} 사용 가능)
	public String getQuery() {
		return (query != null) ? query : "";
	}

	// ✅ Setters & Getters (필요한 경우 추가)
	public int getPage() {
		return page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isShowPrev() {
		return showPrev;
	}

	public boolean isShowNext() {
		return showNext;
	}
}
