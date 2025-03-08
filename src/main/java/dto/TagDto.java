package dto;

import java.util.Objects;

public class TagDto {
	private int tag_id;
	private String tag_name;

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public TagDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TagDto(int tag_id, String tag_name) {
		super();
		this.tag_id = tag_id;
		this.tag_name = tag_name;
	}

	@Override
	public String toString() {
		return "TagDto [tag_id=" + tag_id + ", tag_name=" + tag_name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(tag_id, tag_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagDto other = (TagDto) obj;
		return tag_id == other.tag_id && Objects.equals(tag_name, other.tag_name);
	}
}
