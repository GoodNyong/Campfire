package dto;

import java.util.Date;
import java.util.Objects;

public class BoardDto {
	private int board_id;
	private String category;
	private int writer_id;
	private String id;
	private String title;
	private String content;
	private int view_count;
	private Date reg_date;


	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(int writer_id) {
		this.writer_id = writer_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	public BoardDto() {}

	public BoardDto(int board_id, String category, int writer_id, String id, String title, String content,
			int view_count, Date reg_date) {
		super();
		this.board_id = board_id;
		this.category = category;
		this.writer_id = writer_id;
		this.id = id;
		this.title = title;
		this.content = content;
		this.view_count = view_count;
		this.reg_date = reg_date;
	}

	
	public BoardDto(String category, int writer_id, String id, String title, String content) {
		super();
		this.category = category;
		this.writer_id = writer_id;
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public BoardDto(String category, String title, String content, String id) {
		super();
		this.category = category;
		this.title = title;
		this.content = content;
		this.id = id;
	}

	public BoardDto( String title, String content, String id) {
		super();
		this.title = title;
		this.content = content;
		this.id = id;
	}


	@Override
	public String toString() {
		return "BoardDto [board_id=" + board_id + ", category=" + category + ", writer_id=" + writer_id + ", id=" + id
				+ ", title=" + title + ", content=" + content + ", view_count=" + view_count + ", reg_date=" + reg_date
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(board_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardDto other = (BoardDto) obj;
		return board_id == other.board_id;
	}

}
