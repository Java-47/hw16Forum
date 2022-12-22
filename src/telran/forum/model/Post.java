package telran.forum.model;

import java.time.LocalDateTime;

public class Post implements Comparable<Post> {
	private String author;
	private int postId;
	private String title;
	private String content;
	private LocalDateTime date;
	private int likes;

	public Post(String author, int postId, String title, String content, LocalDateTime date, int likes) {
		this.author = author;
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.date = date;
		this.likes = likes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public int getPostId() {
		return postId;
	}

	public String getTitle() {
		return title;
	}

	public int getLikes() {
		return likes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + postId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Post)) {
			return false;
		}
		Post other = (Post) obj;
		if (postId != other.postId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Post [author=" + author + ", postId=" + postId + ", title=" + title + ", content=" + content + ", date="
				+ date + ", likes=" + likes + "]";
	}

	public int addLike() {
		return ++likes;
	}

	@Override
	public int compareTo(Post o) {
		return date.compareTo(o.date);
	}

}
