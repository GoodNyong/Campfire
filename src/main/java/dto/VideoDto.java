package dto;

import java.util.Date;
import java.util.Objects;

public class VideoDto {
	private int video_id;
	private String title;
	private String thumbnail_url;
	private String channel_name;
	private Date published_dt;
	private String video_url;
	private String channel_url;

	public int getVideo_id() {
		return video_id;
	}

	public void setVideo_id(int video_id) {
		this.video_id = video_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public Date getPublished_dt() {
		return published_dt;
	}

	public void setPublished_dt(Date published_dt) {
		this.published_dt = published_dt;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getChannel_url() {
		return channel_url;
	}

	public void setChannel_url(String channel_url) {
		this.channel_url = channel_url;
	}

	public VideoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VideoDto(int video_id, String title, String thumbnail_url, String channel_name, Date published_dt,
			String video_url, String channel_url) {
		super();
		this.video_id = video_id;
		this.title = title;
		this.thumbnail_url = thumbnail_url;
		this.channel_name = channel_name;
		this.published_dt = published_dt;
		this.video_url = video_url;
		this.channel_url = channel_url;
	}

	@Override
	public String toString() {
		return "VideoDto [video_id=" + video_id + ", title=" + title + ", thumbnail_url=" + thumbnail_url
				+ ", channel_name=" + channel_name + ", published_dt=" + published_dt + ", video_url=" + video_url
				+ ", channel_url=" + channel_url + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(video_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VideoDto other = (VideoDto) obj;
		return video_id == other.video_id;
	}

}
