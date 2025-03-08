package dto;

import java.util.Objects;

public class Board_TagDto {
	private int board_id;
	private int tag_id;

	@Override
	public int hashCode() {
		return Objects.hash(board_id, tag_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board_TagDto other = (Board_TagDto) obj;
		return board_id == other.board_id && tag_id == other.tag_id;
	}

	@Override
	public String toString() {
		return "Board_TagDto [board_id=" + board_id + ", tag_id=" + tag_id + "]";
	}

	public Board_TagDto(int board_id, int tag_id) {
		super();
		this.board_id = board_id;
		this.tag_id = tag_id;
	}

	public Board_TagDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
}
