package dto;

import java.util.Date;
import java.util.Objects;

public class ReviewDto {
	private int review_id;
	private int game_id;
	private int member_id;
	private int rating;
	private String content;
	private Date reg_date;

	public int getReview_id() {
		return review_id;
	}

	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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

	public ReviewDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewDto(int review_id, int game_id, int member_id, int rating, String content, Date reg_date) {
		super();
		this.review_id = review_id;
		this.game_id = game_id;
		this.member_id = member_id;
		this.rating = rating;
		this.content = content;
		this.reg_date = reg_date;
	}

	@Override
	public String toString() {
		return "ReiviewDto [review_id=" + review_id + ", game_id=" + game_id + ", member_id=" + member_id + ", rating="
				+ rating + ", content=" + content + ", reg_date=" + reg_date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(review_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewDto other = (ReviewDto) obj;
		return review_id == other.review_id;
	}

}
