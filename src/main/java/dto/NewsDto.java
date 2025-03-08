package dto;

import java.util.Date;
import java.util.Objects;

public class NewsDto {
	private int news_id;
	private String title;
	private String originallink;
	private String link;
	private String description;
	private Date pubDate;

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginallink() {
		return originallink;
	}

	public void setOriginallink(String originallink) {
		this.originallink = originallink;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public NewsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewsDto(int news_id, String title, String originallink, String link, String description, Date pubDate) {
		super();
		this.news_id = news_id;
		this.title = title;
		this.originallink = originallink;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "NewsDto [news_id=" + news_id + ", title=" + title + ", originallink=" + originallink + ", link=" + link
				+ ", description=" + description + ", pubDate=" + pubDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(news_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsDto other = (NewsDto) obj;
		return news_id == other.news_id;
	}
}