package dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class GameDto {
	private int game_id;
	private String game_name;
	private String genre;
	private Date release_dt;
	private String description;
	private String game_still_cut;
	private BigDecimal avg_rating;

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public String getGame_name() {
		return game_name;
	}

	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getRelease_dt() {
		return release_dt;
	}

	public void setRelease_dt(Date release_dt) {
		this.release_dt = release_dt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGame_still_cut() {
		return game_still_cut;
	}

	public void setGame_still_cut(String game_still_cut) {
		this.game_still_cut = game_still_cut;
	}

	public BigDecimal getAvg_rating() {
		return avg_rating;
	}

	public void setAvg_rating(BigDecimal avg_rating) {
		this.avg_rating = avg_rating;
	}

	public GameDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GameDto(int game_id, String game_name, String genre, Date release_dt, String description,
			String game_still_cut, BigDecimal avg_rating) {
		super();
		this.game_id = game_id;
		this.game_name = game_name;
		this.genre = genre;
		this.release_dt = release_dt;
		this.description = description;
		this.game_still_cut = game_still_cut;
		this.avg_rating = avg_rating;
	}

	@Override
	public String toString() {
		return "GameDto [game_id=" + game_id + ", game_name=" + game_name + ", genre=" + genre + ", release_dt="
				+ release_dt + ", description=" + description + ", game_still_cut=" + game_still_cut + ", avg_rating="
				+ avg_rating + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(game_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameDto other = (GameDto) obj;
		return game_id == other.game_id;
	}

}
