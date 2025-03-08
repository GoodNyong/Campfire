package dto;

import java.util.Date;
import java.util.Objects;

public class CommentDto {
	private int comment_id;
	private int board_id;
	private int writer_id;
	private String id;
	private String content;
	private Date reg_date;

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentDto(int comment_id, int board_id, int writer_id, String id, String content, Date reg_date) {
		super();
		this.comment_id = comment_id;
		this.board_id = board_id;
		this.writer_id = writer_id;
		this.id = id;
		this.content = content;
		this.reg_date = reg_date;
	}

	@Override
	public String toString() {
		return "CommentDto [comment_id=" + comment_id + ", board_id=" + board_id + ", writer_id=" + writer_id + ", id="
				+ id + ", content=" + content + ", reg_date=" + reg_date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentDto other = (CommentDto) obj;
		return comment_id == other.comment_id;
	}

}
