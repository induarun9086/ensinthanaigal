package com.ensinthanaigal.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;


@Entity
public class Post {

	private Long postID;
	private int category;
	@Lob
	private String title;
	@Lob
	private String content;
	@Lob
	private String tags;
	private Long postedAt;
	private Long modifiedAt;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getPostID() {
		return postID;
	}

	public void setPostID(Long postID) {
		this.postID = postID;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Long postedAt) {
		this.postedAt = postedAt;
	}

	public Long getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
