package dto;

import java.util.Date;
import java.util.Objects;

public class LikeDto {
	private int like_id;
	private int board_id;
	private int comment_id;
	private int member_id;
	private Date reg_date;

	public int getLike_id() {
		return like_id;
	}

	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public LikeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LikeDto(int like_id, int board_id, int comment_id, int member_id, Date reg_date) {
		super();
		this.like_id = like_id;
		this.board_id = board_id;
		this.comment_id = comment_id;
		this.member_id = member_id;
		this.reg_date = reg_date;
	}

	@Override
	public String toString() {
		return "LikeDto [like_id=" + like_id + ", board_id=" + board_id + ", comment_id=" + comment_id + ", member_id="
				+ member_id + ", reg_date=" + reg_date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(like_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LikeDto other = (LikeDto) obj;
		return like_id == other.like_id;
	}

}
